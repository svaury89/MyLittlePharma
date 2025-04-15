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
import com.example.ui.extension.textAnalyserImageProxy
import com.example.ui.navigation.EanProductNavigation
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun ScanDateScreen(
    navigationController: NavHostController,
    ean: String
) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoldText(
            modifier = Modifier.padding(16.dp),
            title = R.string.scan_date_title
        )
        CameraScreen(
            onProcessImage = { imageProxy, cameraProvider ->
                imageProxy.textAnalyserImageProxy(
                    recognizer = recognizer,
                    onFinishScan = { date ->
                        navigationController.navigate(EanProductNavigation(ean = ean, date = date))
                        cameraProvider.unbindAll()
                    }

                )
            }

        )
    }
}