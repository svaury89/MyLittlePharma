package com.example.data.firebase

import com.example.data.exception.FireBaseException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.data.room.model.Product

class FirebaseDao {

    suspend fun writeProductOnFirebase(product: Product) {

        val dbReference = Firebase.database.getReference("products")
        dbReference.setValue(product)
            .addOnFailureListener { exception ->
                throw FireBaseException(exception, exception.message)
            }
    }

    suspend fun getProductFromFirebase() : List<Product> {
        val dbReference = Firebase.database.getReference("products")
        val result = dbReference.get().result
        return if(result.exists()){
            result.children.mapNotNull { it.getValue(Product::class.java) }
        }else{
             emptyList()
        }

    }
}