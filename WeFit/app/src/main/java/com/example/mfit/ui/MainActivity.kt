package com.example.mfit.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mfit.App
import com.example.mfit.R
import com.example.mfit.databinding.ActivityMainBinding
import com.example.mfit.services.StepDetectorService
import com.example.mfit.interfaces.stepsCallback

class MainActivity : AppCompatActivity(), stepsCallback {

    private lateinit var binding: ActivityMainBinding
    // Initializing the viewModel on call using KTX-Fragments extension.
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
        supportActionBar?.hide()
        checkAndRequestPermissions()
        initStepsDetectorService()

    }

    private fun checkAndRequestPermissions(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION), 500);
        }
    }

    private fun initStepsDetectorService(){
        StepDetectorService.subscribe.register(this)
        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)
    }

    override fun subscribeSteps(steps: Int) {
        viewModel.updateSteps(steps);
    }
}