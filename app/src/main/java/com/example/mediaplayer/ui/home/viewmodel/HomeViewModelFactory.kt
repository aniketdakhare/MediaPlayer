package com.example.mediaplayer.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.VideoRepository

class HomeViewModelFactory(private val videoRepository: VideoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(videoRepository) as T
    }
}
