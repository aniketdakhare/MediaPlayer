package com.example.mediaplayer.data

import android.net.Uri
import com.example.mediaplayer.data.model.VideoDetails
import com.example.mediaplayer.util.Failed
import com.example.mediaplayer.util.FailingReason.OTHER
import com.example.mediaplayer.util.Loading
import com.example.mediaplayer.util.Status
import com.example.mediaplayer.util.Succeed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class VideoRepository : IVideoRepository {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var mStorageRef: StorageReference

    init {
        initService()
    }

    private fun initService() {
        firebaseAuth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    override fun uploadVideo(videoDetails: VideoDetails, callback: (Status) -> Unit) {
        val fileRef =
            mStorageRef.child("${VIDEO_COLLECTION}/${videoDetails.fileName}")
        fileRef.putFile(videoDetails.videoFile)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    uploadVideoToFireStore(videoDetails.videoTitle, uri, callback)
                }
            }.addOnProgressListener {
                val percent = (100 * it.bytesTransferred) / it.totalByteCount
                callback(Loading("Uploaded: $percent %"))
            }
    }

    private fun uploadVideoToFireStore(videoTitle: String, uri: Uri, callback: (Status) -> Unit) {
        val videoDetails: MutableMap<String, Any> = HashMap()

        videoDetails[VIDEO_LOWERCASE_TITLE] = videoTitle.toLowerCase()
        videoDetails[VIDEO_TITLE] = videoTitle
        videoDetails[VIDEO_URL] = uri.toString()
        videoDetails[USER] = firebaseAuth.currentUser!!.uid

        fireStore.collection(VIDEO_COLLECTION).document()
            .set(videoDetails)
            .addOnSuccessListener { callback(Succeed("Video Uploaded Successfully")) }
            .addOnFailureListener { callback(Failed("Error while Uploading video", OTHER)) }
    }

    override fun fetchVideosFromFireStore(): CollectionReference {
        return fireStore.collection(VIDEO_COLLECTION)
    }

    override fun getSearchQuery(tittle: String?): Query {
        return fireStore.collection(VIDEO_COLLECTION).orderBy("lowerCaseTitle").startAt(tittle)
            .endAt(tittle + "\uf8ff")
    }

    companion object {
        private const val VIDEO_COLLECTION = "videos"
        private const val VIDEO_TITLE = "title"
        private const val VIDEO_LOWERCASE_TITLE = "lowerCaseTitle"
        private const val VIDEO_URL = "url"
        private const val USER = "user"
    }
}