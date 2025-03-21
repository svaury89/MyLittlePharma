package com.example.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.domain.model.ProductModel
import com.example.ui.R
import com.example.ui.model.ProductUi
import com.example.ui.state.GetProductUiState
import com.example.ui.viewmodel.AddOrUpdateProductViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.Normalizer.Form

@Composable
fun AddOrUpdateProductScreen(
    navController: NavController,
    vm: AddOrUpdateProductViewModel = koinViewModel()
){
    val state by vm.state.collectAsStateWithLifecycle()
    when(state){
        is GetProductUiState.isLoding -> Progress()
        is GetProductUiState.isSuccess ->  Form(navController =  navController,
            onSaveClick = {name,description -> vm.saveProduct(name,description)},
            (state as GetProductUiState.isSuccess).product
        )
    }

}

@Composable
fun Form(
    navController: NavController,
    onSaveClick : (String, String)->Unit ,
    productUi: ProductUi
){
    var name by remember { mutableStateOf(productUi.name) }
    var description by remember { mutableStateOf(productUi.description) }
    var date by remember { mutableStateOf(productUi.date) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){

        Column {
            ProductField(title = R.string.product_name,name) { name = it }
            ProductField(title = R.string.product_description, description){ description = it}
            ProductField(title = R.string.product_date,date){ date = it}
        }
        Button(onClick = {
            onSaveClick(name,description)
            navController.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.save_product))
        }


    }
}

@Composable
fun ProductField(@StringRes title: Int,
                 textFieldValue : String = "",
                 onValueChange : (String) -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold
        )
        TextField(value = textFieldValue, onValueChange = onValueChange)

    }
}