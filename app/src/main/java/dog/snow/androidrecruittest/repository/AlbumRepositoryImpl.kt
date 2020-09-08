package dog.snow.androidrecruittest.repository

import androidx.lifecycle.LiveData
import dog.snow.androidrecruittest.repository.database.AlbumDao
import dog.snow.androidrecruittest.repository.database.PhotoDao
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.net.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class AlbumRepositoryImpl(private val albumDao: AlbumDao,
                          private val dataSource: DataSource
) : AlbumRepository {

    init{
        dataSource.downloadedAlbumData.observeForever{ newAlbums ->
            perstistFetchedAlbums(newAlbums)
        }
    }
    override suspend fun getAlbums(): LiveData<out List<RawAlbum>> {

        return withContext(Dispatchers.IO) {
            initAlbumData()
            return@withContext albumDao.getAlbums()
        }
    }
    private fun perstistFetchedAlbums(fetchedAlbums: List<RawAlbum>){
        GlobalScope.launch(Dispatchers.IO) {
            albumDao.upsert(fetchedAlbums)
        }
    }
    private suspend fun initAlbumData(){
        //if(isFetchNeeded(ZonedDateTime.now().minusDays(2)))
        fetchAlbums()
    }
    private suspend fun fetchAlbums(){
        dataSource.getAlbums()
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