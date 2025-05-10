package com.example.ui.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.example.domain.extension.reformatIfInputIsDate
import com.example.domain.extension.toBarCodeAndDate
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.BarcodeFormat
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
                Log.i("Format","Format "+ it)

                when(barcode.format){
                    Barcode.FORMAT_EAN_13 -> onFinishScan(it,null)
                    Barcode.FORMAT_DATA_MATRIX -> {
                        val result = it.toBarCodeAndDate()
                        Log.i("Format","Format AFTER  "+ result.first + " "+ result.second)
                        onFinishScan(result.first,result.second)
                    }
                }
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