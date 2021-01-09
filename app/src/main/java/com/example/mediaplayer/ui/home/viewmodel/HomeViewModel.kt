package com.example.mediaplayer.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.data.model.Video
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class HomeViewModel(private val service: VideoRepository): ViewModel() {

    fun searchVideos(tittle: String, config: PagedList.Config): FirestorePagingOptions<Video> {
        val query = service.getSearchQuery(tittle.toLowerCase())
        return FirestorePagingOptions.Builder<Video>()
            .setQuery(query, config, Video::class.java)
            .build()
    }

    fun getOptionToFetchVideos(config: PagedList.Config): FirestorePagingOptions<Video> {
        val query = service.fetchVideosFromFireStore()
            return FirestorePagingOptions.Builder<Video>()
                .setQuery(query, config, Video::class.java)
                .build()
    }
}