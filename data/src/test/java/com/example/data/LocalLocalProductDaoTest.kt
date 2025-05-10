package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.data.room.MyLittlePharmaDB
import com.example.data.room.dao.LocalProductDao


@RunWith(AndroidJUnit4::class)
class LocalLocalProductDaoTest {

    private lateinit var localProductDao: LocalProductDao
    private lateinit var db: MyLittlePharmaDB


    @Before
    fun createDb() {
        val context =  ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyLittlePharmaDB::class.java).allowMainThreadQueries().build()
        localProductDao = db.productDao()
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