package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer

import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import androidx.lifecycle.ViewModelProvider
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.ui.adapter.ListAdapter

import java.util.logging.Logger

class ListFragment : ScopedFragment(),KodeinAware {
    val LOG = Logger.getLogger(this.javaClass.name)

    override val kodein by closestKodein()
    private val viewModelFactory: DataFragment by instance()
    private lateinit var adapter : ListAdapter
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.list_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this,viewModelFactory).get(ViewModel::class.java)


        bindUI()
        }

    private fun bindUI() = launch{
        val photos = viewModel.photo.await()
        val albums = viewModel.album.await()
        var users = viewModel.user.await()

        photos.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer
            LOG.warning("TEST"+it.toString())
        })
        albums.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer

            LOG.warning("TEST"+it.toString())
        })
        users.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer

            LOG.warning("TEST"+it.toString())
        })

    }


}