package com.example.ui.composable

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.compose.AsyncImage
import com.example.ui.R
import com.example.ui.model.ProductUi

@Composable
fun FormCard(
    productUi: ProductUi,
    onChangeName:((String)->Unit) ?   = null ,
    onChangeDescription:((String)->Unit) ? = null,
    onChangeDate:(String)->Unit = {},
    photoPickerLauncher : ManagedActivityResultLauncher<PickVisualMediaRequest,Uri?>
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        photoPickerLauncher
                            .launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                            )
                    },
                horizontalArrangement = Arrangement.Center
            )
            {
                if (productUi.image == null) {
                    Image(
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(R.drawable.no_photography),
                        contentDescription = "",
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = productUi.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(id = R.drawable.no_photography),
                    )
                }
            }

            MLPTextField(title = R.string.product_name, textFieldValue =  productUi.name, onValueChange = onChangeName ?: {})
            MLPTextField(title = R.string.product_description, textFieldValue =  productUi.description, onValueChange = onChangeDescription ?: {})
            LimitDate(onUpdateDate =  onChangeDate, date = productUi.date, title = R.string.product_date )
        }

    }
}