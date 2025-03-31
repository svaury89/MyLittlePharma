package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class ErrorDetailAPi (
    @SerializedName("message")
    val errorMessage: ErrorMessage ?
)