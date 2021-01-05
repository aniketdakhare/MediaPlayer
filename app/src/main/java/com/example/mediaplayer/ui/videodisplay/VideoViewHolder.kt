package com.example.mediaplayer.ui.videodisplay

import android.net.Uri
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video

class VideoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val videoView: VideoView = view.findViewById(R.id.videoCard)
    private val tittle: TextView = view.findViewById(R.id.cardTitle)

    fun bind(videoDetails: Video) {
        tittle.text = videoDetails.videoTitle
//        videoView.start()
        videoView.setVideoURI(Uri.parse(videoDetails.videoUrl))
    }
}
