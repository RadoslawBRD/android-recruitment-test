package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.database.UserDao
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.net.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class UserRepositoryImpl(private val userDao: UserDao,
                         private val dataSource: DataSource
) : UserRepository {

    init{
        dataSource.downloadedUserData.observeForever{ newUsers ->
            perstistFetchedUsers(newUsers)
        }
    }

    override suspend fun getUsers(): LiveData<out List<RawUser>> {
        return withContext(Dispatchers.IO) {
            initUserData()
            return@withContext userDao.getUsers()
        }
    }
    private fun perstistFetchedUsers(fetchedUsers: List<RawUser>){
        GlobalScope.launch(Dispatchers.IO) {
            userDao.upsert(fetchedUsers)
        }
    }
    private suspend fun initUserData(){
        //if(isFetchNeeded(ZonedDateTime.now().minusDays(2)))
        fetchUsers()
    }
    private suspend fun fetchUsers(){
        dataSource.getUsers()
    }

    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean{
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val dayAgo = ZonedDateTime.now().minusDays(1 )
            lastFetchTime.isBefore(dayAgo)
        } else {
            true//todo
        }
    }
}