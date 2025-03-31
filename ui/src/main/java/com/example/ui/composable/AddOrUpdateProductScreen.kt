package com.example.ui.composable

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
                navController = navController,
                onSaveClick = { vm.saveProduct(context) },
                productUi = productUi,
                onNameEdit = { vm.updateProductUi(name = it) },
                onDescriptionEdit = { vm.updateProductUi(description = it) },
                onDateEdit = { vm.updateProductUi(date = it) },
                onUpdateImage = {
                    vm.updateImage(it, contentResolver)
                },
                isButtonEnabled = validator
            )
        }
    }

}

@Composable
fun Form(
    navController: NavController,
    onSaveClick: () -> Unit,
    productUi: ProductUi,
    onNameEdit: ((String) -> Unit)? = null,
    onDescriptionEdit: ((String) -> Unit)? = null,
    onDateEdit: (String) -> Unit,
    onUpdateImage: (Uri?) -> Unit,
    isButtonEnabled : Boolean
) {

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) {
            onUpdateImage(it)
        }

    Column {

        FormCard(
            productUi = productUi,
            onChangeName = onNameEdit,
            onChangeDescription = onDescriptionEdit,
            onChangeDate = onDateEdit,
            photoPickerLauncher = photoPickerLauncher
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onSaveClick()
                    navController.popBackStack()
                },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor =  MaterialTheme.colorScheme.primary
                )

            ) {
                BoldText(title = R.string.save_product)
            }
        }
    }
}


@Composable
fun BoldText(@StringRes title: Int) {
    Text(
        text = stringResource(id = title),
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Bold
    )
}

