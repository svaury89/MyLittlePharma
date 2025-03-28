package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ProductDetailAPi(
    @SerializedName("image_front_url")
    val imageUrl: String?,
    @SerializedName("abbreviated_product_name_fr")
    val title:String ?,
    @SerializedName("product_name_fr")
    val content : String ?
)
