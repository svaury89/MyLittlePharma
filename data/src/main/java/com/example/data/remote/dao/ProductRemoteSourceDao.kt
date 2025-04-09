package com.example.data.remote.dao

import android.util.Log
import com.example.data.exception.FireBaseException
import com.example.data.remote.RemoteDatabaseHandler
import com.example.data.room.model.LocalProduct
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
        }.addOnFailureListener{ exception ->
            throw FireBaseException(exception, exception.message)
        }
    }

    override suspend fun updateRealTimeChangeFromRemoteDb(
        onRemoveChild : (LocalProduct) -> Unit,
        onAddOrUpdateChild : (LocalProduct) -> Unit
    ) {
        val dbReference = remoteDatabaseHandler.remoteDb.getReference("products")
        dbReference.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val addProduct = snapshot.getValue(LocalProduct::class.java)
                addProduct?.let (onAddOrUpdateChild)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updateProduct = snapshot.getValue(LocalProduct::class.java)
                updateProduct?.let (onAddOrUpdateChild)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedProduct = snapshot.getValue(LocalProduct::class.java)
                removedProduct?.let(onRemoveChild)

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }
}