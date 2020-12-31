package com.example.mediaplayer.appstartpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.model.IUserService

class AppStartViewModelFactory(private val service: IUserService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppStartViewModel(service) as T
    }
}
