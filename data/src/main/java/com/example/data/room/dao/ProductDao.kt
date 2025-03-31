package com.example.data.room.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.data.room.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): Flow<List<Product>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(product: Product): Int

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product) : Long

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM product where product.uid = :id")
    fun getProductById(id: String): Flow<Product>


    @Transaction
    suspend fun insertOrUpdate(product: Product){
        val id = insert(product)
        if (id==-1L) {
            updateProduct(product)
        }
    }
}