package com.example.domain.model

import java.time.LocalDate
import java.util.UUID

data class ProductDraft(
     val uid: String = UUID.randomUUID().toString(),
     val name: String ,
     val description: String,
     val date: LocalDate = LocalDate.now() ,
     val image : String ? = null ,
     val imageUrl: String ? = null
)

