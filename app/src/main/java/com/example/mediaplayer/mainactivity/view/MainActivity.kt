package com.example.mediaplayer.mainactivity.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.R
import com.example.mediaplayer.appstartpage.view.AppStartFragment
import com.example.mediaplayer.databinding.ActivityMainBinding
import com.example.mediaplayer.home.view.HomeFragment
import com.example.mediaplayer.login.view.LoginFragment
import com.example.mediaplayer.model.UserService
import com.example.mediaplayer.register.view.RegisterFragment
import com.example.mediaplayer.setting.view.SettingFragment
import com.example.mediaplayer.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.sharedviewmodel.SharedViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
        operateBottomNavigation()
        setSupportActionBar(binding.toolbar)
        binding.toolbar.showOverflowMenu()
        goToStartAppPage()
        observeAppNavigation()
    }

    private fun operateBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> goToHomePage()
                R.id.navigation_setting -> goToSettingPage()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initActivity() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        sharedViewModel = ViewModelProvider(
            this, SharedViewModelFactory(UserService())
        )[SharedViewModel::class.java]
    }

    private fun observeAppNavigation() {
        sharedViewModel.goToHomePageStatus.observe(this, {
            if (it == true) goToHomePage()
        })
        sharedViewModel.goToRegisterPageStatus.observe(this, {
            if (it == true) goToRegisterUserPage()
        })
        sharedViewModel.goToLoginPageStatus.observe(this, {
            if (it == true) goToLoginUserPage()
        })
    }

    private fun goToStartAppPage() {
        binding.toolbar.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, AppStartFragment())
            commit()
        }
    }

    private fun goToRegisterUserPage() {
        binding.toolbar.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, RegisterFragment())
            commit()
        }
    }

    private fun goToLoginUserPage() {
        binding.toolbar.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, LoginFragment())
            commit()
        }
    }

    private fun goToHomePage() {
        binding.toolbar.visibility = View.VISIBLE
        binding.bottomNavigation.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, HomeFragment())
            commit()
        }
    }

    private fun goToSettingPage() {
        binding.toolbar.visibility = View.GONE
        binding.bottomNavigation.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, SettingFragment())
            commit()
        }
    }
}