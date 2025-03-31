package com.example.ui.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
    val context = LocalContext.current
    val contentResolver =  context.contentResolver

    when(state){
        GetNetworkProductState.EmptyProduct -> Log.i("ProductByEan ","ProductbyEAN Empty")
        is GetNetworkProductState.Error -> Log.i("ProductByEan ","ProductbyEAN Error "+ (state as GetNetworkProductState.Error).message)
        is GetNetworkProductState.Product ->
            Form(
                navController = navController,
                onSaveClick = { vm.saveProduct(context) },
                productUi = productUi,
                onNameEdit = {vm.updateProductUi(name = it)},
                onDescriptionEdit = {vm.updateProductUi(description = it)},
                onDateEdit = {vm.updateProductUi(date = it)},
                onUpdateImage = {
                    vm.updateImage(it,contentResolver)
                }
            )

        GetNetworkProductState.isLoading -> Progress()
    }

}



