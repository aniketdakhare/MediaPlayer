package com.example.mediaplayer.ui.fullscreen.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mediaplayer.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

class FullScreen : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView
    lateinit var textView: TextView
    var fullscreen = false
    lateinit var fullscreenButton: ImageView
    private lateinit var url: String
    private var playWhenReady = false
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        playerView = findViewById(R.id.exoplayer_fullscreen)
        textView = findViewById(R.id.tv_fullscreen)
        fullscreenButton = playerView.findViewById(R.id.exoplayer_fullscreen_icon)


        val intent = intent
        url = intent.extras!!.getString("url")!!
        val title = intent.extras!!.getString("title")

        textView.text = title

        fullscreenButton.setOnClickListener {
            if (fullscreen) {
             setScreenToPortrait()
            } else {
                setScreenToLandscape()
            }
        }
    }

    private fun setScreenToPortrait() {
        fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.exo_controls_fullscreen_enter))
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        if (supportActionBar != null) {
            supportActionBar!!.show()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val params = playerView.layoutParams as RelativeLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height =
            (200 * applicationContext.resources.displayMetrics.density).toInt()
        playerView.layoutParams = params
        fullscreen = false
        textView.visibility = View.VISIBLE
    }

    private fun setScreenToLandscape() {
        fullscreenButton.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.exo_controls_fullscreen_exit))
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val params = playerView.layoutParams as RelativeLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        playerView.layoutParams = params
        fullscreen = true
        textView.visibility = View.INVISIBLE
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory("videos")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        playerView.player = player
        val uri: Uri = Uri.parse(url)
        val mediaSource = buildMediaSource(uri)
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare(mediaSource, false, false)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 26) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT > 26) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 26) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.let {
            playWhenReady = it.playWhenReady
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            player = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        player?.stop()
        releasePlayer()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}