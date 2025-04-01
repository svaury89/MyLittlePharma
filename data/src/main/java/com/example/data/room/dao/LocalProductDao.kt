package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.data.room.model.LocalProduct

@Dao
interface LocalProductDao {

    @Query("SELECT * FROM localproduct")
    fun getProducts(): Flow<List<LocalProduct>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(localProduct: LocalProduct): Int

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(localProduct: LocalProduct) : Long

    @Delete
    fun deleteProduct(localProduct: LocalProduct)

    @Query("SELECT * FROM localproduct where localproduct.uid = :id")
    fun getProductById(id: String): Flow<LocalProduct>


    @Transaction
    suspend fun insertOrUpdate(localProduct: LocalProduct){
        val id = insert(localProduct)
        if (id==-1L) {
            updateProduct(localProduct)
        }
    }
}