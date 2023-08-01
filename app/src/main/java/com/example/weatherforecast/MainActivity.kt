package com.example.weatherforecast

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherforecast.base.binding.viewBinding
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.ui.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var navController: NavController

    private val onMainBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.label) {
                getString(R.string.label_blank_fragment),
                getString(R.string.label_home_fragment), -> {
                    handleProcessExit()
                }

                else -> {
                    navController.popBackStack()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initGraph()
        initOnBackPressed()
    }
    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, onMainBackPressed)
    }

    private fun initGraph() {
        val navHostFragment = getNavHost()
        navController = navHostFragment.navController
        navController.navigate(R.id.action_blankFragment_to_homeFragment)
    }

    private fun getNavHost() =
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    private fun handleProcessExit() {
        if (mainViewModel.getBackExit()) {
            finishAndRemoveTask()
        } else {
            mainViewModel.setBackExit(true)
            mainViewModel.processToDefaultExit()
        }
    }
}