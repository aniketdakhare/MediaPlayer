package com.example.mediaplayer.ui.videodisplay

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class VideoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val tittle: TextView = view.findViewById(R.id.vtitle)
    private val owner: TextView = view.findViewById(R.id.owner)

    private val simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(view.context.applicationContext)
    private val playerView: PlayerView = (view.findViewById(R.id.exoplayerview) as PlayerView).also {
        it.player = simpleExoPlayer
    }


    fun bind(videoDetails: Video) {
        tittle.text = videoDetails.title
        owner.text = videoDetails.owner
        try {
            val videoURI = Uri.parse(videoDetails.url)

            val dataSourceFactory = DefaultHttpDataSourceFactory("videos")
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
            val mediaSource: MediaSource =
                ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)

            simpleExoPlayer.prepare(mediaSource)
            simpleExoPlayer.repeatMode = SimpleExoPlayer.REPEAT_MODE_ONE
            setPlayerStatus(false)
            simpleExoPlayer.playbackState
            simpleExoPlayer.volume = 0f

        }catch (e: Exception){
            Log.e(TAG, "bind: $e", )
        }
    }

    fun setPlayerStatus(status: Boolean) {
        simpleExoPlayer.playWhenReady = status
    }

    companion object {
        private const val TAG = "VideoViewHolder"
    }
}
