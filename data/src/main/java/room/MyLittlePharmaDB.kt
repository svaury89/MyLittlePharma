package room

import androidx.room.Database
import androidx.room.RoomDatabase
import room.dao.ProductDao
import room.model.Product

@Database(entities = [Product::class], version = 1)
abstract class MyLittlePharmaDB :  RoomDatabase() {

    abstract fun productDao() : ProductDao
}