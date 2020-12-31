package com.example.mediaplayer.mainactivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.ActivityMainBinding
import com.example.mediaplayer.model.UserService
import com.example.mediaplayer.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.sharedviewmodel.SharedViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration =
                AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_setting))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun initActivity() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        sharedViewModel = ViewModelProvider(
            this, SharedViewModelFactory(UserService())
        )[SharedViewModel::class.java]
    }
}