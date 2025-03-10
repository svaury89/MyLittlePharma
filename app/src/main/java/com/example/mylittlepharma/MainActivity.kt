package com.example.mylittlepharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.firebase.FirebaseDao
import com.example.data.room.model.Product
import com.example.mylittlepharma.ui.theme.MyLittlePharmaTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date

class MainActivity : ComponentActivity() {

    val firebaseDao : FirebaseDao by inject<FirebaseDao>()

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
                        firebaseDao.writeProductOnFirebase(
                            Product(
                                name = "test",
                                description = "desc",
                                date = Date()
                            )
                        )
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
    Button(onClick = {
        coroutineScope.launch {
            onClick()
        }

    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyLittlePharmaTheme {
        Greeting("Android", onClick = {})
    }
}