package com.example.mediaplayer.ui.videodisplay

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import kotlin.properties.Delegates


class VideoViewAdapter(
    options: FirestorePagingOptions<Video>,
    val playVideo: (Video) -> Unit
) : FirestorePagingAdapter<Video, VideoViewHolder>(options) {

//    private lateinit var holder: VideoViewHolder
//    private var position by Delegates.notNull<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val displayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_display_layout, parent, false)
        return VideoViewHolder(displayView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.bind(video)
        holder.view.setOnClickListener { playVideo(video) }
//        this.holder = holder
//        this.position = position

    }

//    override fun onAttachedToRecyclerView(videoRecyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(videoRecyclerView)
//        videoRecyclerView.viewTreeObserver.addOnPreDrawListener {
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    videoRecyclerView.viewTreeObserver.removeOnPreDrawListener(this)
//                    videoRecyclerView.addOnScrollListener(object :
//                        RecyclerView.OnScrollListener() {
//                        override fun onScrollStateChanged(
//                            recyclerView: RecyclerView,
//                            newState: Int
//                        ) {
//                            super.onScrollStateChanged(recyclerView, newState)
//                            playCenterVideo(position, holder, videoRecyclerView)
//                        }
//                    })
//                    return true
//                }
//            }
//            return@addOnPreDrawListener true
//        }
//
//    }

    private fun playCenterVideo(
        position: Int,
        holder: VideoViewHolder,
        videoRecyclerView: RecyclerView
    ) {
        val firstItemPosition =
            (videoRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        val lastItemPosition =
            (videoRecyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        val centerPosition = (firstItemPosition + lastItemPosition) / 2
//        val snapHelper = LinearSnapHelper()
//        val snapView = snapHelper.findSnapView(videoRecyclerView.layoutManager)
//        val snapPosition = snapView?.let {
//            (videoRecyclerView.layoutManager as LinearLayoutManager).getPosition(it) }

        Log.e(TAG, "playCenterVideo: POSITION: $position :: SNAPPOSITION: $centerPosition")

        if (centerPosition == position){
            Log.e(TAG, "playCenterVideo: INSIDE  IF TRUE")
            holder.setPlayerStatus(true)
        }
    }

    companion object {
        private const val TAG = "VideoViewAdapter"
    }

}
