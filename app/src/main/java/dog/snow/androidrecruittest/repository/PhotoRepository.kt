package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.model.RawPhoto

interface PhotoRepository {
    suspend fun getPhotos(): LiveData<out List<RawPhoto>>

}