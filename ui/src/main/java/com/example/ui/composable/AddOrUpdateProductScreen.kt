package com.example.ui.composable

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.model.ProductUi
import com.example.ui.state.GetProductUiState
import com.example.ui.viewmodel.AddOrUpdateProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddOrUpdateProductScreen(
    navController: NavController,
    vm: AddOrUpdateProductViewModel = koinViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val productUi by vm.productUi.collectAsStateWithLifecycle()
    val validator by vm.validFormState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val contentResolver = context.contentResolver


    when (state) {
        GetProductUiState.isLoding -> {
            Progress()
        }

        is GetProductUiState.isSuccess -> {
            Form(
                onSaveClick = {
                    vm.saveProduct(context)
                    navController.popBackStack()
                },
                productUi = productUi,
                onNameEdit = { vm.updateProductUi(name = it) },
                onDescriptionEdit = { vm.updateProductUi(description = it) },
                onDateEdit = { vm.updateProductUi(date = it) },
                onUpdateImage = {
                    vm.updateImage(it, contentResolver)
                },
                onTakePicture = {vm.updateImage(it)},
                isButtonEnabled = validator
            )
        }
    }

}





