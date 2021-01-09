package com.example.mediaplayer.data

import com.example.mediaplayer.data.model.VideoDetails
import com.example.mediaplayer.util.Status
import com.google.firebase.firestore.Query

interface IVideoRepository {
    fun uploadVideo(videoDetails: VideoDetails, callback: (Status) -> Unit)
    fun fetchVideosFromFireStore(): Query
    fun getSearchQuery(tittle: String?): Query
}