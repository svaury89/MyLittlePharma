package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.module.provideDao
import kotlinx.coroutines.flow.Flow
import com.example.data.room.model.LocalProduct
import kotlinx.coroutines.flow.count

@Dao
interface LocalProductDao {

    @Query("SELECT * FROM localproduct")
    fun getProducts(): Flow<List<LocalProduct>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(localProduct: LocalProduct): Int

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(localProduct: LocalProduct) : Long

    @Query("Delete FROM localproduct where uid = :id")
    fun deleteProductById(id : String )

    @Query("SELECT * FROM localproduct where localproduct.uid = :id")
    fun getProductById(id: String): Flow<LocalProduct>

    @Query("SELECT COUNT() > 0 FROM localproduct where localproduct.uid = :id")
    fun isProductExist(id: String): Boolean


    @Transaction
    suspend fun insertOrUpdate(localProduct: LocalProduct){
        val id = insert(localProduct)
        if (id==-1L) {
            updateProduct(localProduct)
        }
    }

    @Transaction
    suspend fun deleteProductIfExist(localProduct: LocalProduct){
        if(isProductExist(localProduct.uid)){
            deleteProductById(localProduct.uid)
        }
    }
}