package dog.snow.androidrecruittest.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import dog.snow.androidrecruittest.repository.model.*

@Database(entities = [RawAlbum::class,RawUser::class,RawPhoto::class], version = 1)//zmień wersje przy braku integralności danych
abstract class MainDatabase: RoomDatabase(){

    abstract fun photosDao() :PhotoDao
    abstract fun usersDao():UserDao
    abstract fun albumsDao():AlbumDao

    companion object{
        @Volatile private var instance: MainDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK)
        {
            instance?: buildDatabase(context).also {instance = it}
        }
        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                MainDatabase::class.java, "zadanko.db")
                .build()
        }
    }
