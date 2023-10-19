package com.miftah.mysubmissionintermediate.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.miftah.mysubmissionintermediate.R
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.ActivityMainBinding
import com.miftah.mysubmissionintermediate.feature.auth.WelcomeActivity
import com.miftah.mysubmissionintermediate.feature.main.data.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) {
            if (!it.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }else {
                viewModel.saveToken(it.token)
            }
        }
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_profile, R.id.navigation_listStory
        ).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController(R.id.nav_host_fragment_activity_main).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}