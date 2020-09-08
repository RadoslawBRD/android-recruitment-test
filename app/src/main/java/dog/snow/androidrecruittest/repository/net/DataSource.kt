package dog.snow.androidrecruittest.repository.net

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser


interface DataSource {
    val downloadedPhotoData: LiveData<List<RawPhoto>>
    val downloadedAlbumData: LiveData<List<RawAlbum>>
    val downloadedUserData: LiveData<List<RawUser>>

    suspend fun getPhotos(  )
    suspend fun getAlbums( )
    suspend fun getUsers( )
    suspend fun getAlbum(
        id:String
    )
    suspend fun getUser(
        id:String
    )

}