package com.example.mediaplayer.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.IUserRepository

class RegisterViewModelFactory(private val userRepository: IUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(userRepository) as T
    }
}
