package com.example.mediaplayer.ui.videouploadpage.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.R
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.data.VideoRepository
import com.example.mediaplayer.data.model.VideoDetails
import com.example.mediaplayer.databinding.FragmentUploadVideoBinding
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory
import com.example.mediaplayer.ui.videouploadpage.viewmodel.UploadVideoViewModel
import com.example.mediaplayer.ui.videouploadpage.viewmodel.UploadVideoViewModelFactory
import com.example.mediaplayer.util.Failed
import com.example.mediaplayer.util.Loading
import com.example.mediaplayer.util.Succeed
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
            this, UploadVideoViewModelFactory(VideoRepository())
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload_video, container, false)
        binding.uploadVideoViewModel = uploadVideoViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaController = MediaController(requireContext())
        binding.videoView.setMediaController(mediaController)
        binding.videoView.start()
        binding.browseButton.setOnClickListener {
            val openGalleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(openGalleryIntent, 220)
        }
        binding.uploadButton.setOnClickListener {
            uploadVideo()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        (activity as AppCompatActivity).toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.popBackStack()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 220 && resultCode == Activity.RESULT_OK)
            intent?.data?.let {
                videoUri = it
                binding.videoView.setVideoURI(it)
            }
    }

    private fun uploadVideo() {
        binding.videoView.pause()
        val pd = ProgressDialog(requireContext())
        pd.setTitle("Video Uploader")
        pd.show()

        val title = binding.videoTittle.text.toString()
        val fileName = "${System.currentTimeMillis()}.${getExtension()}"
        uploadVideoViewModel.uploadVideo(VideoDetails(videoUri, title, fileName))

        uploadVideoViewModel.videoUploadingStatus.observe(viewLifecycleOwner, {
            when (it) {
                is Succeed ->{
                    pd.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.videoView.resume()
                }
                is Failed ->{
                    pd.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.videoView.resume()
                }
                is Loading -> pd.setMessage(it.message)
            }
        })
    }

    private fun getExtension(): String {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(context?.contentResolver?.getType(videoUri))
            .toString()
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.setUploadMenuStatus(true)
    }

}