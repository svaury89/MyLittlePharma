package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ErrorMessage (
    @SerializedName("id")
    val message : String ?
)
