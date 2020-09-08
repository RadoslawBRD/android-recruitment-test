package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.model.RawAlbum

interface AlbumRepository {
    suspend fun getAlbums(): LiveData<out List<RawAlbum>>

}