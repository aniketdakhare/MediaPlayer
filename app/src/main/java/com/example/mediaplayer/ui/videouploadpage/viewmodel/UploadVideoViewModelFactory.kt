package com.example.mediaplayer.ui.videouploadpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UploadVideoViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UploadVideoViewModel() as T
    }
}
