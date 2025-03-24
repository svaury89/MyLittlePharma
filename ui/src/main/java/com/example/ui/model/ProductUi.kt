package com.example.ui.model

import coil3.Bitmap

data class ProductUi (
    val uuid: String = "",
    val name : String = "",
    val description : String = "",
    val date: String = "",
    val image : Bitmap ? = null
)