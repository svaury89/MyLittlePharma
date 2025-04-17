package com.example.ui.composable

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.model.ProductUi


@Composable
fun Form(
    onSaveClick: () -> Unit,
    productUi: ProductUi,
    onNameEdit: ((String) -> Unit)? = null,
    onDescriptionEdit: ((String) -> Unit)? = null,
    onDateEdit: (String) -> Unit,
    onUpdateImage: (Uri?) -> Unit,
    onTakePicture : (Bitmap) -> Unit,
    isButtonEnabled: Boolean
) {

    val launchCamera = remember { mutableStateOf(false) }
    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) {
            launchCamera.value = false
            onUpdateImage(it)
        }

    Column {

        FormCard(
            productUi = productUi,
            onChangeName = onNameEdit,
            onChangeDescription = onDescriptionEdit,
            onChangeDate = onDateEdit,
            onLaunchCamera = { launchCamera.value = true }
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onSaveClick()

                },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                )

            ) {
                BoldText(title = R.string.save_product)
            }
        }
    }
    if (launchCamera.value) {
        CameraLaunch(
            onDismiss = { launchCamera.value = false },
            onLaunchGalery = {
                photoPickerLauncher
                    .launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                    )
            },
            onProcessImage = {
                onTakePicture(it.toBitmap())
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraLaunch(
    onDismiss: () -> Unit,
    onLaunchGalery: () -> Unit,
    onProcessImage: (ImageProxy) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraScreen(
                onProcessImage =
                    onProcessImage
            )
            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    onLaunchGalery()
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.no_photography),
                    contentDescription = ""
                )
            }
        }
    }

}