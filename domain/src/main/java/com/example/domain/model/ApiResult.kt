package com.example.domain.model

sealed class ApiResult {
    data class Error(val message : String?) : ApiResult()
    data class Success(val productModel: ProductModel?) : ApiResult()
}