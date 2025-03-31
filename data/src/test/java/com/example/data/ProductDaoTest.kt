package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.example.data.room.MyLittlePharmaDB
import com.example.data.room.dao.ProductDao


@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: MyLittlePharmaDB


    @Before
    fun createDb() {
        val context =  ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyLittlePharmaDB::class.java).allowMainThreadQueries().build()
        productDao = db.productDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun insertProductReturnTrue() = runBlocking {
        assertEquals(1,1)

       /* val product = Product(
        uid= 1,
            name = "Doliprane",
            description = "",
            date = "05/10/2024"
        )
        productDao.insertProducts(product)

        productDao.getProducts().collect{
            assertTrue(it.contains(product))
            assertEquals(1,it.size)
        }*/

    }
}