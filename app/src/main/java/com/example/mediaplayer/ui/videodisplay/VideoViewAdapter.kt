package com.example.mediaplayer.ui.videodisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Video
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class VideoViewAdapter(
    options: FirestorePagingOptions<Video>,
) : FirestorePagingAdapter<Video, VideoViewHolder>(options) {

//    private val allVideos = mutableListOf<Video>().apply {
//        addAll(videos)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val displayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_display_layout, parent, false)
        return VideoViewHolder(displayView)
    }

//    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
//        val video = allVideos[position]
//        holder.bind(video)
//        holder.view.setOnClickListener {
//        }
//    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, video: Video) {
        holder.bind(video)
        holder.view.setOnClickListener {  }
    }

//    override fun getFilter(): Filter {
//        return searchFilter
//    }
//
//    private val searchFilter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val filteredList = mutableListOf<Video>()
//
//            if (constraint.toString().isEmpty())
//                filteredList.addAll(videos)
//            else {
//                for (note in videos) {
//                    if (note.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        filteredList.add(note)
//                    }
//                }
//            }
//
//            val filterResults = FilterResults()
//            filterResults.values = filteredList
//
//            return filterResults
//        }
//
//        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            allVideos.clear()
//            allVideos.addAll(results?.values as Collection<Video>)
//            notifyDataSetChanged()
//        }
//    }
}
