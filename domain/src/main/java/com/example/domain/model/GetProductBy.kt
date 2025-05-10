package com.example.domain.model

sealed interface GetProductBy {
    data class ProductId(val id: String?) : GetProductBy
    data class Ean(val ean: String) : GetProductBy
}