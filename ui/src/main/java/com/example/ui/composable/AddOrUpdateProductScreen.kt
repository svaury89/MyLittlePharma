package com.example.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
                onTakePicture = { vm.updateImage(it) },
                isButtonEnabled = validator
            )
        }
    }

}





