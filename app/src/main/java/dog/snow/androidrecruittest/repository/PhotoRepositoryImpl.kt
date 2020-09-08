package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.database.PhotoDao
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.net.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class PhotoRepositoryImpl( private val photoDao: PhotoDao,
                           private val dataSource: DataSource
) : PhotoRepository {

    init{
        dataSource.downloadedPhotoData.observeForever{ newPhotos ->
            perstistFetchedPhotos(newPhotos)
        }
    }

    override suspend fun getPhotos(): LiveData<out List<RawPhoto>> {
        return withContext(Dispatchers.IO) {
            initPhotoData()
            return@withContext photoDao.getPhotos()
        }
    }
    private fun perstistFetchedPhotos(fetchedPhotos: List<RawPhoto>){
        GlobalScope.launch(Dispatchers.IO) {
            photoDao.upsert(fetchedPhotos)
        }
    }
    private suspend fun initPhotoData(){
    //if(isFetchNeeded(ZonedDateTime.now().minusDays(2)))
        fetchPhotos()
    }
    private suspend fun fetchPhotos(){
        dataSource.getPhotos()
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