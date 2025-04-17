package com.example.ui.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.example.domain.extension.reformatIfInputIsDate
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalGetImage::class)
fun ImageProxy.scanImageProxy(
    barcodeScanner: BarcodeScanner,
    onFinishScan: (String, String ?) -> Unit,
) {
    this.image?.let { image ->
        val inputImage = InputImage.fromMediaImage(image, this.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage).addOnSuccessListener { barcodeList ->
            val barcode = barcodeList.getOrNull(0)
            barcode?.rawValue?.let {
                onFinishScan(it,null)
            }
        }.addOnCompleteListener {
            image.close()
            this.close()
        }
    }

}

@OptIn(ExperimentalGetImage::class)
fun ImageProxy.textAnalyserImageProxy(
    onFinishScan: (String ) -> Unit,
    recognizer: TextRecognizer
) {
    this.image?.let { image ->
        val inputImage = InputImage.fromMediaImage(image, this.imageInfo.rotationDegrees)
        recognizer.process(inputImage).addOnSuccessListener { text ->
            text.text.split("\n").forEach{
                val result = it.reformatIfInputIsDate()
                if(result != null){
                    onFinishScan(result)
                }
            }
        }.addOnCompleteListener {
            image.close()
            this.close()
        }
    }

}

fun ImageProxy.toBitmap() : Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}