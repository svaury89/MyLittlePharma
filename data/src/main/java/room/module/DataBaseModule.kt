package room.module

import android.app.Application
import androidx.room.Room
import room.dao.ProductDao
import org.koin.dsl.module
import room.MyLittlePharmaDB


fun provideDataBase(application: Application): MyLittlePharmaDB =
    Room.databaseBuilder(
        application,
        MyLittlePharmaDB::class.java,
        "table_product"
    ).fallbackToDestructiveMigration().build()

fun provideDao(myLittlePharmaDB: MyLittlePharmaDB): ProductDao = myLittlePharmaDB.productDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}
