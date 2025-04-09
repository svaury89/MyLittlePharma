package com.example.data.api.model

sealed interface GetProductBy {
    data class ProductId(val id: String) : GetProductBy
    data class Ean(val ean: String) : GetProductBy
}