package com.example.mediaplayer.ui.appstartpage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.IUserRepository

class AppStartViewModel(private val repository: IUserRepository): ViewModel() {

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn = _isUserLoggedIn as LiveData<Boolean>

    fun checkUserExistence() {
        repository.getLoginStatus{
            _isUserLoggedIn.value = it
        }
    }
}