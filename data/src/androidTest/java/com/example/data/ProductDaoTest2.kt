package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.example.data.room.MyLittlePharmaDB
import com.example.data.room.dao.ProductDao
import com.example.data.room.model.Product
import kotlinx.coroutines.test.runTest
import java.util.Date
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
class ProductDaoTest2 {

    private lateinit var productDao: ProductDao
    private lateinit var db: MyLittlePharmaDB
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun createDb() {

        Dispatchers.setMain(testDispatcher)
        val context =  ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyLittlePharmaDB::class.java).allowMainThreadQueries().build()
        productDao = db.productDao()
    }

    @After
    fun closeDatabase() {
        db.close()

        Dispatchers.resetMain()
    }

    @Test
    fun insertProductReturnTrue() = runTest {

        val product = Product(
        uid= "1",
            name = "Doliprane",
            description = "",
            date = Date().time
        )
        productDao.insert(product)

        val latch = CountDownLatch(1)
        productDao.getProducts().collect{
            assertTrue(it.contains(product))
            assertEquals(1,it.size)
        }

        latch.await()


    }
}