package dog.snow.androidrecruittest

import android.content.Context
import android.os.Bundle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dog.snow.androidrecruittest.repository.database.MainDatabase
import dog.snow.androidrecruittest.repository.database.MainDatabase_Impl
import dog.snow.androidrecruittest.repository.database.PhotoDao
import dog.snow.androidrecruittest.ui.ListFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.logging.Logger


class MainActivity : AppCompatActivity(R.layout.main_activity){
    val LOG = Logger.getLogger(this.javaClass.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        LOG.warning( "onCreate:MAIN ")
        LOG.warning(MainDatabase.invoke(context = this).albumsDao().getAlbums().toString())
        ListFragment()

    }



}