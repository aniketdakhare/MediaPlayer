package com.example.mediaplayer.ui.videodisplay

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class VideoViewAdapter(
    options: FirestorePagingOptions<Video>,
    private val application: Application,
) : FirestorePagingAdapter<Video, VideoViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val displayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_display_layout, parent, false)
        return VideoViewHolder(displayView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.bind(video, application)
        holder.view.setOnClickListener {  }
    }
}
