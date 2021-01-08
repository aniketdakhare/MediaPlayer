package com.example.mediaplayer.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
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

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: VideoViewAdapter
    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(6)
        .setPageSize(3)
        .build()

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
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.videoRecyclerView.adapter = adapter

//        val snapHelper = LinearSnapHelper()
//        val snapView = snapHelper.findSnapView(binding.videoRecyclerView.layoutManager)
//        val snapPosition = snapView?.let {
//            (binding.videoRecyclerView.layoutManager as LinearLayoutManager).getPosition(it) }
//        val demo = (binding.videoRecyclerView.layoutManager as LinearLayoutManager).findViewByPosition(5)
        binding.videoRecyclerView.viewTreeObserver.addOnPreDrawListener {
            Log.e(TAG, "onViewCreated: addOnPreDrawListener")
            playCenterVideo(binding.videoRecyclerView)
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Log.e(TAG, "onPreDraw: ", )
                    binding.videoRecyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                    binding.videoRecyclerView.addOnScrollListener(object :
                        RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            Log.e(TAG, "onScrollStateChanged: ", )
                            super.onScrollStateChanged(recyclerView, newState)
                            playCenterVideo(binding.videoRecyclerView)
                        }
                    })
                    return true
                }
            }
            return@addOnPreDrawListener true
        }

        sharedViewModel.queryText.observe(viewLifecycleOwner, {
            adapter = VideoViewAdapter(homeViewModel.searchVideos(it, config)) { video ->
                sharedViewModel.setVideoToPlayOnFullScreen(video)
            }
            adapter.startListening()
            binding.videoRecyclerView.adapter = adapter
        })
    }

    private fun playCenterVideo(recyclerView: RecyclerView) {
        Log.e(TAG, "playCenterVideo: ")
        val firstItemPosition =
            (binding.videoRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        val lastItemPosition =
            (binding.videoRecyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        val centerPosition = (firstItemPosition + lastItemPosition) / 2
        val demo = binding.videoRecyclerView.findViewHolderForAdapterPosition(firstItemPosition)
        (demo as VideoViewHolder).setPlayerStatus( true)
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