package com.example.mediaplayer.setting.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.FragmentSettingBinding
import com.example.mediaplayer.model.UserService
import com.example.mediaplayer.setting.viewmodel.SettingViewModel
import com.example.mediaplayer.setting.viewmodel.SettingViewModelFactory
import com.example.mediaplayer.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.sharedviewmodel.SharedViewModelFactory

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(UserService())
        )[SettingViewModel::class.java]
        sharedViewModel = ViewModelProvider(
            requireActivity(), SharedViewModelFactory(UserService())
        )[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.settingViewModel = settingViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SignOutButton.setOnClickListener {
            settingViewModel.logout()
            settingViewModel.logoutStatus.observe(viewLifecycleOwner, {
                sharedViewModel.setGoToLoginPageStatus(true)
                sharedViewModel.setGoToHomePageStatus(false)
            })
        }
        binding.profileImageView.setOnClickListener {
            val openGalleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(openGalleryIntent, 200)
        }
        setProfileDetails()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 200 && resultCode == Activity.RESULT_OK)
            intent?.data?.let {
                settingViewModel.uploadImageToFirebase(it)
                Glide.with(this).load(it).into(binding.profileImageView)
                sharedViewModel.setImageUri(it)
            }
    }

    private fun setProfileDetails() {
        sharedViewModel.userDetails.observe(viewLifecycleOwner, {
            binding.profileName.text = it.fullName
            binding.profileEmail.text = it.email
            if (it.imageUrl != "")
                Glide.with(this).load(it.imageUrl).into(binding.profileImageView)
        })
    }
}