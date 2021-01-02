package com.example.mediaplayer.ui.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.IUserRepository

class SharedViewModelFactory(private val userRepository: IUserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(userRepository) as T
    }
}
