package dog.snow.androidrecruittest.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawAlbum

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(AlbymsEntry: List<RawAlbum>)

    @Query( "select * from album")
    fun getAlbums(): LiveData<List<RawAlbum>>

    @Query("select * from album where id =(:id)")
    fun getAlbumById(id:String): LiveData<RawAlbum>
}