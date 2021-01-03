package com.example.mediaplayer.ui.videouploadpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.IVideoRepository

class UploadVideoViewModelFactory(private val videoRepository: IVideoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UploadVideoViewModel(videoRepository) as T
    }
}
