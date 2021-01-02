package com.example.mediaplayer.ui.appstartpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.IUserRepository

class AppStartViewModelFactory(private val repository: IUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AppStartViewModel(repository) as T
    }
}
