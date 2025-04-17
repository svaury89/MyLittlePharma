package com.example.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ui.R

import com.example.ui.extension.scanImageProxy
import com.example.ui.navigation.EanProductNavigation
import com.example.ui.navigation.ScanDateNavigation
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode


@Composable
fun ScanProductScreen(
    navigationController: NavHostController
) {
    val option =
        BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
    val scanner = BarcodeScanning.getClient(option)

    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoldText(
            modifier = Modifier.padding(16.dp),
            title = R.string.scan_bar_code_title
        )

        CameraScreen(
            onProcessImage = { imageProxy ->
                imageProxy.scanImageProxy(
                    barcodeScanner = scanner,
                    onFinishScan = { ean, date ->
                        if (date == null) {
                            navigationController.navigate(ScanDateNavigation(ean))
                        }
                    }

                )
            }

        )
    }

}

