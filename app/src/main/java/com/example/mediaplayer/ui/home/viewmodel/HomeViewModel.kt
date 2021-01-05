package com.example.mediaplayer.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.data.model.Video
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class HomeViewModel(private val service: VideoRepository): ViewModel() {

    private val _videosList = MutableLiveData<FirestorePagingOptions<Video>>()
    val videosList = _videosList as LiveData<FirestorePagingOptions<Video>>

    private fun getAllVideos() {
//        service.fetchVideosFromFireStore() {
//            _videosList.value = FirestorePagingOptions.Builder<Video>()
//                .setQuery(it, config, Video::class.java)
//                .build()
//        }
    }

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

    init {
        getAllVideos()
    }
}