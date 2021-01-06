package com.example.mediaplayer.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaplayer.R
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.databinding.FragmentHomeBinding
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModel
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModelFactory
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory
import com.example.mediaplayer.ui.videodisplay.VideoViewAdapter

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

        adapter = VideoViewAdapter(homeViewModel.getOptionToFetchVideos(config), requireActivity().application){
            sharedViewModel.setVideoToPlayOnFullScreen(it)
        }
        binding.videosList.layoutManager = LinearLayoutManager(requireContext())
        binding.videosList.adapter = adapter

        sharedViewModel.queryText.observe(viewLifecycleOwner, {
            adapter = VideoViewAdapter(homeViewModel.searchVideos(it, config), requireActivity().application){ video ->
                sharedViewModel.setVideoToPlayOnFullScreen(video)
            }
            adapter.startListening()
            binding.videosList.adapter = adapter
        })
    }



    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}