package com.example.mediaplayer.data

import android.net.Uri
import com.example.mediaplayer.data.model.User
import com.facebook.AccessToken

interface IUserRepository {
    fun authenticateUser(email: String, password: String, listener: (Boolean) -> Unit)
    fun registerUser(user: User, listener: (Boolean) -> Unit)
    fun resetPassword(emailId: String, listener: (Boolean) -> Unit)
    fun facebookLogin(token: AccessToken, listener: (User, Boolean) -> Unit)
    fun logoutUser()
    fun getUserDetails(listener: (User) -> Unit)
    fun uploadImageToFirebase(uri: Uri)
    fun getLoginStatus(listener: (Boolean) -> Unit)
}