package com.example.ui.model

import coil3.Bitmap
import java.util.UUID

data class ProductUi (
    val uuid: String = UUID.randomUUID().toString(),
    val name : String = "",
    val description : String = "",
    val date: String = "",
    val image : Bitmap ? = null ,
    val dateState: DateState = DateState.VALID
)