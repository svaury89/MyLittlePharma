package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ProductApi (
    @SerializedName("product")
    val product : ProductDetailAPi?,
    @SerializedName("errors")
    val errors :  List<ErrorDetailAPi>?
)