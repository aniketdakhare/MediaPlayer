package com.example.mediaplayer.ui.videouploadpage.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mediaplayer.R
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.databinding.FragmentUploadVideoBinding
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory
import com.example.mediaplayer.ui.videouploadpage.viewmodel.UploadVideoViewModel
import com.example.mediaplayer.ui.videouploadpage.viewmodel.UploadVideoViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentUploadVideoBinding
    private lateinit var videoUri: Uri
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadVideoViewModel = ViewModelProvider(
            this, UploadVideoViewModelFactory()
        )[UploadVideoViewModel::class.java]
        sharedViewModel = ViewModelProvider(
            requireActivity(), SharedViewModelFactory(UserRepository())
        )[SharedViewModel::class.java]
        sharedViewModel.setUploadMenuStatus(false)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_video, container, false)
        binding.uploadVideoViewModel = uploadVideoViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaController = MediaController(requireContext())
        binding.videoView.setMediaController(mediaController)
        binding.videoView.start()
        binding.browseButton.setOnClickListener{
            val openGalleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(openGalleryIntent, 220)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 220 && resultCode == Activity.RESULT_OK)
            intent?.data?.let {
                binding.videoView.setVideoURI(it)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        (activity as AppCompatActivity).toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.popBackStack()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.setUploadMenuStatus(true)
    }

}