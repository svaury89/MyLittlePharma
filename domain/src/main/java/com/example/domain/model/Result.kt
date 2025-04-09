package com.example.domain.model

sealed class Result {
    data class Error(val message : String?) : Result()
    data class Success(val product: Product?) : Result()
}