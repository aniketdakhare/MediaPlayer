package com.example.mediaplayer.ui.videodisplay

import android.app.Application
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource

import com.google.android.exoplayer2.source.MediaSource

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory

import com.google.android.exoplayer2.extractor.ExtractorsFactory

import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

import com.google.android.exoplayer2.ExoPlayerFactory

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter

import com.google.android.exoplayer2.upstream.BandwidthMeter




class VideoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val simpleExoPlayerView: SimpleExoPlayerView = view.findViewById(R.id.exoplayerview)
    private val tittle: TextView = view.findViewById(R.id.vtitle)
    private lateinit var simpleExoPlayer: SimpleExoPlayer

    fun bind(videoDetails: Video, application: Application) {
        tittle.text = videoDetails.title

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val trackSelector: TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(application, trackSelector) as SimpleExoPlayer

        val videoURI = Uri.parse(videoDetails.url)

        val dataSourceFactory = DefaultHttpDataSourceFactory("videos")
        val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
        val mediaSource: MediaSource =
            ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)

        simpleExoPlayerView.player = simpleExoPlayer
        simpleExoPlayer.prepare(mediaSource)
        simpleExoPlayer.playWhenReady = false
    }
}
