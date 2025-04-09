package com.example.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.ui.R
import com.example.ui.model.ProductUi
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.navigation.ScanProductNavigation
import com.example.ui.state.GetProductsUiState
import com.example.ui.viewmodel.ProductListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    vm: ProductListViewModel = koinViewModel(),
    navController: NavHostController
) {

    val state by vm.productState.collectAsStateWithLifecycle()
    val context  = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Button(
                onClick = { vm.syncProducts(context= context) }
            ) {
                Text(text = stringResource(id = R.string.synchronize))
            }
            when (state) {
                is GetProductsUiState.isSuccess ->
                    ProductList(
                        products = (state as GetProductsUiState.isSuccess).products,
                        onProductSelected = { navController.navigate(AddOrUpdateProductNavigation(it)) },
                        onProductDelete = {vm.deleteProduct(it)}
                    )

                is GetProductsUiState.EmptyList ->
                   EmptyContent(content = R.string.empty_list)

                is GetProductsUiState.isLoding -> Progress()
            }

        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.tertiary,
            onClick = {
                navController.navigate(AddOrUpdateProductNavigation())
            },
        ) {
            Icon(Icons.Filled.Add, "Add Product")
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.tertiary,
            onClick = {
                navController.navigate(ScanProductNavigation)
            },
        ) {
            Icon(painterResource(id = R.drawable.ic_scanner_24), "Scan Product")
        }
    }


}

@Composable
fun ProductList(
    products: List<ProductUi>,
    onProductDelete: (String) -> Unit,
    onProductSelected: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(products) { product ->
            ProductDetail(productUi = product, onDeleted = onProductDelete, onProductSelected = onProductSelected)
        }

    }
}

@Composable
fun ProductDetail(productUi: ProductUi, onProductSelected: (String) -> Unit, onDeleted : (String) ->Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductSelected(productUi.uuid) }
            .padding(bottom = 8.dp, top = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background), //important
                verticalArrangement = Arrangement.Center
            ) {
                if (productUi.image != null) {
                    AsyncImage(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = productUi.image,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        )
                } else {
                    Image(
                        painterResource(R.drawable.no_photography),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                }

            }
            Column(modifier = Modifier.padding(8.dp).weight(3f)) {
                TitleText(title = R.string.product_name, value = productUi.name)
                TitleText(title = R.string.product_description, value = productUi.description)
                TitleText(title = R.string.product_date, value = productUi.date.toString())
            }
            Button(
                onClick = {onDeleted(productUi.uuid)},
                modifier = Modifier.wrapContentSize(),
                content = {
                    Image(
                        painterResource(R.drawable.delete_24),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                }
            )

        }

    }
}

@Composable
fun TitleText(@StringRes title: Int, value: String) {
    Row {
        Text(
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.tertiary,
        )

    }
}