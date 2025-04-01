package com.example.domain.model

import java.time.LocalDate

data class Product(
    val uid: String ,
    val name: String,
    val description: String ,
    val date: LocalDate ,
    val image : String ? ,
    val imageUrl: String ?
)