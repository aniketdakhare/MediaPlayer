package com.example.mediaplayer.ui.appstartpage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.R
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.databinding.FragmentAppStartBinding
import com.example.mediaplayer.ui.appstartpage.viewmodel.AppStartViewModel
import com.example.mediaplayer.ui.appstartpage.viewmodel.AppStartViewModelFactory
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory

class AppStartFragment : Fragment() {
    private lateinit var appStartViewModel: AppStartViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentAppStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_start, container, false)
        appStartViewModel = ViewModelProvider(
            this,
            AppStartViewModelFactory(UserRepository())
        ).get(AppStartViewModel::class.java)
        sharedViewModel = ViewModelProvider(
            requireActivity(), SharedViewModelFactory(UserRepository())
        )[SharedViewModel::class.java]
        binding.appStartViewModel = appStartViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appStartProgressbar.visibility = View.VISIBLE
    }

    private fun setUser() {
        appStartViewModel.checkUserExistence()
        appStartViewModel.isUserLoggedIn.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    sharedViewModel.setGoToHomePageStatus(true)
                    binding.appStartProgressbar.visibility = View.GONE
                }
                false -> {
                    sharedViewModel.setGoToLoginPageStatus(true)
                    binding.appStartProgressbar.visibility = View.GONE
                }
            }
        })
    }

    override fun onStart() {
        setUser()
        super.onStart()
    }
}