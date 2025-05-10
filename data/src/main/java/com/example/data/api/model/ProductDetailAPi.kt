package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ProductDetailAPi(
    @SerializedName("image_front_url")
    val imageUrl: String?,
    @SerializedName("brands")
    val title:String ?,
    @SerializedName("product_name_fr")
    val content : String ?
)
