package com.example.mediaplayer.ui.videouploadpage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.IVideoRepository
import com.example.mediaplayer.data.model.VideoDetails
import com.example.mediaplayer.util.Status

class UploadVideoViewModel(private val service: IVideoRepository): ViewModel() {
    private val _videoUploadingStatus = MutableLiveData<Status>()
    val videoUploadingStatus = _videoUploadingStatus as LiveData<Status>

    fun uploadVideo(videoDetails: VideoDetails) {
        service.uploadVideo(videoDetails){
            _videoUploadingStatus.value = it
        }
    }
}