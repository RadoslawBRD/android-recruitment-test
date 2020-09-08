package dog.snow.androidrecruittest.ui

import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.repository.AlbumRepository
import dog.snow.androidrecruittest.repository.PhotoRepository
import dog.snow.androidrecruittest.repository.UserRepository
import dog.snow.androidrecruittest.repository.internal.lazyDeferred


class ViewModel (private val photoRepository: PhotoRepository,
                private val albumRepository: AlbumRepository,
                private val userRepository: UserRepository): ViewModel()
            {
                val photo by lazyDeferred{ photoRepository.getPhotos() }
                val album by lazyDeferred{ albumRepository.getAlbums() }
                val user by lazyDeferred{ userRepository.getUsers() }


            }