package com.example.mediaplayer.ui.sharedviewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.IUserRepository
import com.example.mediaplayer.data.model.User

class SharedViewModel(
    private val userRepository: IUserRepository,
) : ViewModel() {

    init {
        fetchUserDetails()
    }

    private val _uploadMenuStatus = MutableLiveData<Boolean>()
    val uploadMenuStatus = _uploadMenuStatus as LiveData<Boolean>

    private val _goToRegisterPageStatus = MutableLiveData<Boolean>()
    val goToRegisterPageStatus = _goToRegisterPageStatus as LiveData<Boolean>

    private val _goToLoginPageStatus = MutableLiveData<Boolean>()
    val goToLoginPageStatus = _goToLoginPageStatus as LiveData<Boolean>

    private val _goToHomePageStatus = MutableLiveData<Boolean>()
    val goToHomePageStatus = _goToHomePageStatus as LiveData<Boolean>

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri = _imageUri as LiveData<Uri>

    private val _userDetails = MutableLiveData<User>()
    val userDetails = _userDetails as LiveData<User>

    private val _queryText = MutableLiveData<String>()
    val queryText = _queryText as LiveData<String>

    fun setQueryText(string: String?) {
        _queryText.value = string
    }

    fun setGoToRegisterPageStatus(status: Boolean) {
        _goToRegisterPageStatus.value = status
    }

    fun setGoToLoginPageStatus(status: Boolean) {
        _goToLoginPageStatus.value = status
    }

    fun setGoToHomePageStatus(status: Boolean) {
        _goToHomePageStatus.value = status
    }

    fun setImageUri(imageUri: Uri) {
        _imageUri.value = imageUri
    }

    fun fetchUserDetails() {
        userRepository.getUserDetails() {
            _userDetails.value = it
        }
    }

    fun setUserDetails(user: User) {
        _userDetails.value = user
    }

    fun setUploadMenuStatus(status: Boolean) {
        _uploadMenuStatus.value = status
    }

    companion object {
        private const val TAG = "SharedViewModel"
    }
}