package com.example.mediaplayer.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.model.IUserService

class SharedViewModelFactory(private val userService: IUserService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(userService) as T
    }
}
