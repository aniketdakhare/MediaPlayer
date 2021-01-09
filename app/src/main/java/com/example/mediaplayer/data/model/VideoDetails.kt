package com.example.mediaplayer.data.model

import android.net.Uri

data class VideoDetails(
    val videoFile: Uri,
    val videoTitle: String,
    var fileName: String = "",
    var owner: String = ""
)
