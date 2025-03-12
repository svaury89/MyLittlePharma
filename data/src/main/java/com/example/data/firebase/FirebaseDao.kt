package com.example.data.firebase

import android.util.Log
import com.example.data.exception.FireBaseException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.data.room.model.Product
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class FirebaseDao {

    suspend fun writeProductOnFirebase(product: Product) {

        val dbReference = Firebase.database.getReference("products")
        dbReference.child(product.uid).setValue(product)
            .addOnFailureListener { exception ->
                throw FireBaseException(exception, exception.message)
            }
    }

    suspend fun getProductFromFirebase() : List<Product>  {
        val dbReference = Firebase.database.getReference("products")
        val task = dbReference.get()
        task.await()
        val result = task.result
        return if(result.exists()){

            val map =  result.value as Map<String,Product>
            Log.i("FIREBASE","FIREBASE Test "+ map.values)
            map.values.toList()
        }else{
            emptyList()
        }

    }
}