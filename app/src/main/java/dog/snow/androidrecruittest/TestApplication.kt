package dog.snow.androidrecruittest

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dog.snow.androidrecruittest.repository.*
import dog.snow.androidrecruittest.repository.database.MainDatabase
import dog.snow.androidrecruittest.repository.net.DataSource
import dog.snow.androidrecruittest.repository.net.DataSourceImpl
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import dog.snow.androidrecruittest.ui.DataFragment
import dog.snow.androidrecruittest.ui.DetailsFragment
import dog.snow.androidrecruittest.ui.ListFragment

import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class TestApplication : Application(),KodeinAware
{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@TestApplication))

        bind() from singleton { MainDatabase(instance()) }

        bind() from singleton { instance<MainDatabase>().photosDao() }
        bind() from singleton { instance<MainDatabase>().albumsDao() }
        bind() from singleton { instance<MainDatabase>().usersDao() }
        bind() from singleton { AlbumService() }
        bind() from singleton { PhotoService() }
        bind() from singleton { UserService() }

        bind<DataSource>() with singleton { DataSourceImpl(instance(),instance(),instance()) }
        bind<PhotoRepository>() with singleton { PhotoRepositoryImpl(instance(),instance()) }
        bind<AlbumRepository>() with singleton { AlbumRepositoryImpl(instance(),instance()) }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance(),instance()) }

        bind() from provider { DataFragment(instance(),instance(),instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)


    }
}