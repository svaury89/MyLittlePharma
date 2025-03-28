package com.example.ui.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.model.ProductUi
import com.example.ui.state.GetNetworkProductState
import com.example.ui.viewmodel.AddOrUpdateProductViewModel
import com.example.ui.viewmodel.ProductByEanViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductByEanScreen(
    navController: NavController,
    vm: ProductByEanViewModel = koinViewModel()
) {
    Log.i("ProductByEan ","ProductbyEAN Screen ")

    val state by vm.state.collectAsStateWithLifecycle()
    when(state){
        GetNetworkProductState.EmptyProduct -> Log.i("ProductByEan ","ProductbyEAN Empty")
        is GetNetworkProductState.Error -> Log.i("ProductByEan ","ProductbyEAN Error "+ (state as GetNetworkProductState.Error).message)
        is GetNetworkProductState.Product -> ProductDetail(
            productUi = (state as GetNetworkProductState.Product).productUi,
            navController = navController
        )
        GetNetworkProductState.isLoading -> Progress()
    }

}

@Composable
fun ProductDetail(
    productUi: ProductUi,
    navController: NavController
){
    Form(
        navController = navController,
        onSaveClick = { },
        productUi = productUi,
        onNameEdit = {},
        onDescriptionEdit = {},
        onDateEdit = {}
    ) {

    }

}

