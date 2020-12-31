package com.example.mediaplayer.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.model.UserService

class LoginViewModelFactory(private val userService: UserService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userService) as T
    }
}
