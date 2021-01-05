package com.example.mediaplayer.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.data.model.Video

class HomeViewModel(private val service: VideoRepository): ViewModel() {


    private val _videosList = MutableLiveData<List<Video>>()
    val videosList = _videosList as LiveData<List<Video>>

    private fun getAllVideos() {
        service.fetchVideosFromFireStore {
            _videosList.value = it
        }
    }

    init {
        getAllVideos()
    }

}