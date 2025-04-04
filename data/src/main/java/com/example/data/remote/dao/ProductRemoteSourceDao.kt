package com.example.data.remote.dao

import android.util.Log
import com.example.data.exception.FireBaseException
import com.example.data.remote.RemoteDatabaseHandler
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.data.room.model.LocalProduct
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class ProductRemoteSourceDao(
    private val remoteDatabaseHandler: RemoteDatabaseHandler
) : ProductRemoteSource {

    override fun writeProduct(localProduct: LocalProduct) {
        val dbReference = remoteDatabaseHandler.remoteDb.getReference("products")
        dbReference.child(localProduct.uid).setValue(localProduct)
            .addOnFailureListener { exception ->
                throw FireBaseException(exception, exception.message)
            }
    }

    override suspend fun getProduct(): List<LocalProduct> {
        val dbReference = remoteDatabaseHandler.remoteDb.getReference("products")
        val task = dbReference.get()
        task.await()
        val result = task.result
        return if(result.exists()){
            val map =  result.getValue<Map<String,LocalProduct>>()?: emptyMap()
            map.values.toList()
        }else{
            emptyList()
        }
    }

    override fun deleteProductById(id: String) {
       val dbReference =  remoteDatabaseHandler.remoteDb.getReference("products")
        dbReference.child(id).removeValue().addOnSuccessListener {
            Log.i("Delete success","Delete Success")
        }.addOnFailureListener{ exception ->
            throw FireBaseException(exception, exception.message)
        }
    }
}