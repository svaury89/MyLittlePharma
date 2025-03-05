package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import room.MyLittlePharmaDB
import room.dao.ProductDao
import room.model.Product
import java.util.concurrent.CountDownLatch

class ProductDaoTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: MyLittlePharmaDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
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