package com.example.mediaplayer.ui.setting.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.IUserRepository

class SettingViewModel(private val repository: IUserRepository): ViewModel() {
    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus = _logoutStatus as LiveData<Boolean>

    fun logout() {
        _logoutStatus.value = true
        repository.logoutUser()
    }

    fun uploadImageToFirebase(uri: Uri) {
        repository.uploadImageToFirebase(uri)
    }
}