package dog.snow.androidrecruittest.repository.net

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import java.lang.Exception

class DataSourceImpl (
    private val photoService:PhotoService,
    private val albumService: AlbumService,
    private val userService: UserService
                    ): DataSource {

    private val _downloadedPhotoData = MutableLiveData<List<RawPhoto>>()
    override val downloadedPhotoData: LiveData<List<RawPhoto>>
        get() = _downloadedPhotoData

    private val _downloadedAlbumData = MutableLiveData<List<RawAlbum>>()
    override val downloadedAlbumData: LiveData<List<RawAlbum>>
        get() = _downloadedAlbumData

    private val _downloadedUserData = MutableLiveData<List<RawUser>>()
    override val downloadedUserData: LiveData<List<RawUser>>
        get() = _downloadedUserData


    override suspend fun getPhotos() {
        try{
            val downloaded_data = photoService
                .getPhotos()
                .await()
            _downloadedPhotoData.postValue(downloaded_data)
        }catch (e: Exception){
            Log.e("Connectivity","No internet connection", e)
        }
    }

    override suspend fun getAlbums() {
        try{
            val downloaded_data = albumService
                .getAlbums()
                .await()
            _downloadedAlbumData.postValue(downloaded_data)
        }catch (e: Exception){
            Log.e("Connectivity","No internet connection", e)
        }
    }

    override suspend fun getUsers() {
        try{
            val downloaded_data = userService
                .getUsers()
                .await()
            _downloadedUserData.postValue(downloaded_data)
        }catch (e: Exception){
            Log.e("Connectivity","No internet connection", e)
        }
    }

    override suspend fun getAlbum(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: String) {
        TODO("Not yet implemented")
    }
}