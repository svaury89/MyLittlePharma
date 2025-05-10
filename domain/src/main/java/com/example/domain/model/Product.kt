package com.example.domain.model

import java.time.LocalDate
import java.util.UUID

data class Product(
    val uid: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String ,
    val date: LocalDate ,
    val image : String ? ,
    val imageUrl: String ?
)