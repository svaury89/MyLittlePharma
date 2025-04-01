package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.data.room.MyLittlePharmaDB
import com.example.data.room.dao.LocalProductDao
import com.example.data.room.model.LocalProduct
import kotlinx.coroutines.test.runTest
import java.util.Date
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
class LocalLocalProductDaoTest2 {

    private lateinit var localProductDao: LocalProductDao
    private lateinit var db: MyLittlePharmaDB
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun createDb() {

        Dispatchers.setMain(testDispatcher)
        val context =  ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyLittlePharmaDB::class.java).allowMainThreadQueries().build()
        localProductDao = db.productDao()
    }

    @After
    fun closeDatabase() {
        db.close()

        Dispatchers.resetMain()
    }

    @Test
    fun insertProductReturnTrue() = runTest {

        val localProduct = LocalProduct(
        uid= "1",
            name = "Doliprane",
            description = "",
            date = Date().time
        )
        localProductDao.insert(localProduct)

        val latch = CountDownLatch(1)
        localProductDao.getProducts().collect{
            assertTrue(it.contains(localProduct))
            assertEquals(1,it.size)
        }

        latch.await()


    }
}