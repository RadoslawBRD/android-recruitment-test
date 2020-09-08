package dog.snow.androidrecruittest.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawPhoto

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(PhotosEntry: List<RawPhoto>)

    @Query( "select * from photos")
    fun getPhotos(): LiveData<List<RawPhoto>>
}