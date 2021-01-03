package com.example.mediaplayer.ui.mainactivity.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mediaplayer.R
import com.example.mediaplayer.ui.appstartpage.view.AppStartFragment
import com.example.mediaplayer.databinding.ActivityMainBinding
import com.example.mediaplayer.ui.home.view.HomeFragment
import com.example.mediaplayer.ui.login.view.LoginFragment
import com.example.mediaplayer.data.UserRepository
import com.example.mediaplayer.ui.register.view.RegisterFragment
import com.example.mediaplayer.ui.setting.view.SettingFragment
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModel
import com.example.mediaplayer.ui.sharedviewmodel.SharedViewModelFactory
import com.example.mediaplayer.ui.videouploadpage.view.UploadVideoFragment
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var profileImage: CircleImageView
    private var menuStatus = true


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
            this, SharedViewModelFactory(UserRepository())
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
        sharedViewModel.uploadMenuStatus.observe(this, {
            menuStatus = it
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

    private fun goToUploadVideoPage() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, UploadVideoFragment())
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuStatus) {
            menuInflater.inflate(R.menu.home_toolbar_menu, menu)

            val profileItem = menu?.findItem(R.id.profile_menu)
            val view = MenuItemCompat.getActionView(profileItem)
            profileImage = view.findViewById(R.id.toolbar_profile_image)
            sharedViewModel.imageUri.observe(this, {
                if (it != null) Glide.with(this).load(it).into(profileImage)
            })
            sharedViewModel.userDetails.observe(this, {
                if (it.imageUrl != "")
                    Glide.with(this).load(it.imageUrl).into(profileImage)
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.upload -> goToUploadVideoPage()
        }
        return super.onOptionsItemSelected(item)
    }
}