package com.example.mediaplayer.setting.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.model.IUserService

class SettingViewModel(private val service: IUserService): ViewModel() {
    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus = _logoutStatus as LiveData<Boolean>

    fun logout() {
        _logoutStatus.value = true
        service.logoutUser()
    }

    fun uploadImageToFirebase(uri: Uri) {
        service.uploadImageToFirebase(uri)
    }
}