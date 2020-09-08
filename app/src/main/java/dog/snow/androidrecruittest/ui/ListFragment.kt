package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.android.synthetic.main.list_fragment.*

import java.util.logging.Logger

class ListFragment : ScopedFragment(),KodeinAware {
    val LOG = Logger.getLogger(this.javaClass.name)

    override val kodein by closestKodein()
    private val viewModelFactory: DataFragment by instance()
    private lateinit var listAdapter : ListAdapter
    private lateinit var viewModel: ViewModel
    private lateinit var manager : RecyclerView.LayoutManager


    private var userList: List<RawUser> = ArrayList()
    private var itemList: MutableList<ListItem> = mutableListOf()
    private var albumsList: List<RawAlbum> = ArrayList()
    private var photosList: List<RawPhoto> = ArrayList()

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
            photosList=it

            LOG.warning("TEST:Photos"+it.toString())
        })
        albums.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer
            albumsList=it

            LOG.warning("TEST:Albums"+it.toString())
        })
        users.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer
            userList=it
            if(photos!=null)
                itemCreator()
            LOG.warning("TEST:Users"+it.toString())
        })




    }

    private fun initRecyclerView(){
        rv_items.apply {
                manager= LinearLayoutManager(requireContext())
                listAdapter =ListAdapter()
                adapter=listAdapter
        }

    }
    private fun itemCreator() {
        var iterator: Int = 0
        for (photo in photosList) {

            itemList.add(
                ListItem(
                    iterator,
                    photo.title,
                    albumsList.get(photo.albumId - 1).title,
                    photo.thumbnailUrl
                )
            )
            iterator += 1
        }
        if(itemList.isNotEmpty()){
            rv_items.visibility = View.VISIBLE
            initRecyclerView()
            listAdapter.submittList(itemList)

        }
    }



}