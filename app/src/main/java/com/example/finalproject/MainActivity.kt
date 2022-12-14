package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import com.example.finalproject.navigation.AddBottomBarNavigation
import com.example.finalproject.navigation.AddNavigationContent
import com.example.finalproject.systemui.SystemUiNavigationNar
import com.example.finalproject.systemui.SystemUiStatusBar
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val viewModel: MainViewModel by viewModels {
        MainViewModel((application as AppApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setIsWeatherLoad(false)
        viewModel.setIsWeatherCityLoad(false)
        viewModel.setIsLocationLoad(false)
        viewModel.setIsLocationCityLoad(false)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {

            val isDarkTheme = remember { mutableStateOf(appPreferences.getDarkMode()) }
            val isFahrenheit = remember { mutableStateOf(appPreferences.getFahrenheit()) }

            FinalProjectTheme {

                MaterialTheme(colors = if(isDarkTheme.value) darkColors() else lightColors())
                {
                    SystemUiStatusBar(window = window).setStatusBarColor(MaterialTheme.colors.primary, isDarkTheme.value)
                    SystemUiNavigationNar(window = window).setNavigationBarColor(MaterialTheme.colors.primary, isDarkTheme.value)

                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        val navController = rememberNavController()

                        Scaffold(
                            bottomBar = {
                                AddBottomBarNavigation(context = this@MainActivity,
                                    navController = navController)
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = it.calculateBottomPadding())
                            ) {
                                AddNavigationContent(context = this@MainActivity,
                                    navController = navController,
                                    isDarkTheme = isDarkTheme,
                                    isFahrenheit = isFahrenheit,
                                    appPreferences = appPreferences)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!viewModel.navigationStack.value!!.empty()) {
            val top = viewModel.navigationStack.value!!.pop()
            when(top.route) {
                "Home" -> viewModel.setIndexBottomNavigation(0)
                "Search" -> viewModel.setIndexBottomNavigation(1)
                "Favorites" -> viewModel.setIndexBottomNavigation(2)
                "Settings" -> viewModel.setIndexBottomNavigation(3)
            }
        }
        super.onBackPressed()
    }

    fun logout(auth: FirebaseAuth)
    {
        auth.signOut()
        Toast.makeText(applicationContext, "Successful logout",
            Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}



