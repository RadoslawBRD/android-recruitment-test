package dog.snow.androidrecruittest.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dog.snow.androidrecruittest.repository.AlbumRepository
import dog.snow.androidrecruittest.repository.PhotoRepository
import dog.snow.androidrecruittest.repository.UserRepository

class DataFragment (
    private val photoRepository : PhotoRepository,
    private val albumRepository: AlbumRepository,
    private val userRepository: UserRepository
    )    : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ViewModel(photoRepository, albumRepository, userRepository) as T

        }
}