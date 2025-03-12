package com.example.mylittlepharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.firebase.FirebaseDao
import com.example.data.room.model.Product
import com.example.domain.model.ProductModel
import com.example.domain.repository.ProductRepository
import com.example.mylittlepharma.ui.theme.MyLittlePharmaTheme
import com.example.ui.viewmodel.ProductListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date


class MainActivity : ComponentActivity() {

    val repository : ProductRepository by inject<ProductRepository>()
    //val viewModel : ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyLittlePharmaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                       /* repository.addOrUpdateProduct(ProductModel(
                            name = "test1",
                            description = "test2"
                        ))
*/                       repository.syncProductFromFireBase()
                       // viewModel.syncProducts()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onClick :suspend () -> Unit

) {
    val coroutineScope = rememberCoroutineScope()
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                coroutineScope.launch {
                    onClick()
                }

            }) {
            Text(
                text = "click",
                modifier = modifier
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLittlePharmaTheme {
        Greeting("Android", onClick = {})
    }
}