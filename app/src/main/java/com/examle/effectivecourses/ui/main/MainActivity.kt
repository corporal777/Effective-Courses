package com.examle.effectivecourses.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.examle.effectivecourses.App
import com.examle.effectivecourses.navigation.NavGraph
import com.examle.effectivecourses.ui.theme.EffectiveCoursesTheme
import com.examle.effectivecourses.utils.AppBottomBar
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showSplashScreen()
        enableEdgeToEdge()
        setContent {
            EffectiveCoursesTheme(dynamicColor = false) {
                KoinAndroidContext { App() }

                val navController = rememberNavController()
                Scaffold(bottomBar = { AppBottomBar(navController) }) { padding ->
                    NavGraph(navController, padding)
                }
            }

        }

    }

    private fun showSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isSplashShown.value
            }
        }
    }
}