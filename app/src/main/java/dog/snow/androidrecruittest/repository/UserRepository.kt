package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.model.RawUser

interface UserRepository {
    suspend fun getUsers(): LiveData<out List<RawUser>>

}