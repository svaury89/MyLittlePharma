package com.example.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.navigation.HomeScreenNavigation
import com.example.ui.state.GetNetworkProductState
import com.example.ui.viewmodel.ProductByEanViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductByEanScreen(
    navController: NavController,
    vm: ProductByEanViewModel = koinViewModel()
) {

    val state by vm.state.collectAsStateWithLifecycle()
    val productUi by vm.productUi.collectAsStateWithLifecycle()
    val validator by vm.validFormState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    when (state) {
        GetNetworkProductState.EmptyProduct -> EmptyContent(R.string.empty_content)
        is GetNetworkProductState.Error ->
            ErrorContent(
                R.string.error_content,
                (state as GetNetworkProductState.Error).message ?: ""
            )

        is GetNetworkProductState.Product ->
            Form(
                onSaveClick = {
                    vm.saveProduct(context)
                    navController.navigate(HomeScreenNavigation)
                },
                productUi = productUi,
                onNameEdit = { vm.updateProductUi(name = it) },
                onDescriptionEdit = { vm.updateProductUi(description = it) },
                onDateEdit = { vm.updateProductUi(date = it) },
                onUpdateImage = {
                    vm.updateImage(it, contentResolver)
                },
                onTakePicture =   {
                    vm.updateImage(it)
                },
                isButtonEnabled = validator
            )

        GetNetworkProductState.isLoading -> Progress()
    }

}



