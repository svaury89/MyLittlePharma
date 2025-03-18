package com.example.domain.model

import java.util.Date
import java.util.UUID

data class ProductModel (
     val uid: String =  UUID.randomUUID().toString(),
     val name: String = "",
     val description: String = "",
     val date: Date = Date()
)

