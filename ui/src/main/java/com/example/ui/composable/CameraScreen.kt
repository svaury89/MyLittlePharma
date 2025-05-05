package com.example.ui.composable

import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.ui.R
import com.example.ui.extension.getCameraProvider
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Composable
fun CameraScreen(
    onProcessImage: (ImageProxy) -> Unit,
    imageCaptureEnabled: Boolean = true,
    onLaunchGalery: (() -> Unit)? = null
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val imageAnalysis = ImageAnalysis.Builder().build()
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = ImageCapture.Builder().build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        if (imageCaptureEnabled) {
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, imageCapture, preview)
        } else {
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageAnalysis)
            imageAnalysis.setAnalyzer(
                cameraExecutor
            ) {
                onProcessImage(it)
            }
        }
        preview.setSurfaceProvider(previewView.surfaceProvider)

    }

    Column {
        AndroidView({ previewView }, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            if (imageCaptureEnabled) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp),
                    onClick = {
                        takePhoto(
                            cameraExecutor = cameraExecutor,
                            imageCapture = imageCapture,
                            onProcessImage = onProcessImage,
                        )
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Sharp.AddCircle,
                            contentDescription = "Take picture",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(1.dp)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                )
            }
            onLaunchGalery?.let {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(64.dp),
                    onClick = {
                        it()
                    }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(R.drawable.galery_image_24),
                        contentDescription = ""
                    )
                }
            }


        }

    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}

fun takePhoto(
    imageCapture: ImageCapture,
    cameraExecutor: ExecutorService,
    onProcessImage: (ImageProxy) -> Unit,
) {
    imageCapture.takePicture(
        cameraExecutor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                onProcessImage(image)
            }
        })
}



