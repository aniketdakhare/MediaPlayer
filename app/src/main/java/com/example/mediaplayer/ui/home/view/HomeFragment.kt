package com.example.mediaplayer.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaplayer.R
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.databinding.FragmentHomeBinding
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModel
import com.example.mediaplayer.ui.home.viewmodel.HomeViewModelFactory
import com.example.mediaplayer.ui.videodisplay.VideoViewAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: VideoViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(VideoRepository()))[HomeViewModel::class.java]
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
        homeViewModel.videosList.observe(viewLifecycleOwner, {
            adapter = VideoViewAdapter(it)
            binding.videosList.layoutManager = LinearLayoutManager(requireContext())
            binding.videosList.adapter = adapter
        })
    }

}