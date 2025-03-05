package room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import room.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): Flow<List<Product>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProduct(product: Product): Int

    @Insert
    fun insertProducts(vararg products: Product)

    @Delete
    fun deleteProduct(product: Product)
}