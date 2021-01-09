package com.example.mediaplayer.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.databinding.FragmentHomeBinding
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModel
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModelFactory
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory
import com.example.mediaplayer.ui.videodisplay.VideoViewAdapter
import com.example.mediaplayer.ui.videodisplay.VideoViewHolder
import kotlin.properties.Delegates

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: VideoViewAdapter
    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(6)
        .setPageSize(3)
        .build()
    private var holder: VideoViewHolder? by Delegates.vetoable(null){ _, oldValue, newValue ->
        oldValue?.setPlayerStatus(false)
        newValue?.setPlayerStatus(true)
        return@vetoable true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(VideoRepository())
        )[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(
            requireActivity(), SharedViewModelFactory(UserRepository())
        )[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VideoViewAdapter(homeViewModel.getOptionToFetchVideos(config)) {
            sharedViewModel.setVideoToPlayOnFullScreen(it)
        }
        binding.videoRecyclerView.layoutManager = object : LinearLayoutManager(requireContext()){
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                Log.e(TAG, "onLayoutCompleted: $state")
                playCenterVideo()
            }
        }
        binding.videoRecyclerView.adapter = adapter

        binding.videoRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                Log.e(TAG, "onScrollStateChanged: $newState ", )
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) playCenterVideo()
            }
        })

        sharedViewModel.queryText.observe(viewLifecycleOwner, {
            adapter = VideoViewAdapter(homeViewModel.searchVideos(it, config)) { video ->
                sharedViewModel.setVideoToPlayOnFullScreen(video)
            }
            adapter.startListening()
            binding.videoRecyclerView.adapter = adapter
        })
    }

    private fun playCenterVideo() {
        val firstItemPosition =
            (binding.videoRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        if (firstItemPosition == RecyclerView.NO_POSITION) return
        val demo = binding.videoRecyclerView.findViewHolderForAdapterPosition(firstItemPosition)
        Log.e(TAG, "playCenterVideo: firstItemPosition:: $firstItemPosition")
        holder = (demo as VideoViewHolder)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}