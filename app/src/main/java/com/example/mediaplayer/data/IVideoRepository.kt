package com.example.mediaplayer.data

import com.example.mediaplayer.data.model.VideoDetails
import com.example.mediaplayer.util.Status

interface IVideoRepository {
    fun uploadVideo(videoDetails: VideoDetails, callback: (Status) -> Unit)
}