package com.examle.effectivecourses.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.examle.data.data.AppData
import com.examle.effectivecourses.extensions.animComposable
import com.examle.effectivecourses.ui.detail.DetailScreen
import com.examle.effectivecourses.ui.favorite.FavoriteScreen
import com.examle.effectivecourses.ui.home.HomeScreen
import com.examle.effectivecourses.ui.login.LoginScreen
import com.examle.effectivecourses.ui.profile.ProfileScreen
import org.koin.compose.koinInject

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {

    val appData: AppData = koinInject()
    val startDestination by remember { mutableStateOf(if (appData.isLoggedIn) "home" else "login") }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        animComposable(route = "login") {
            LoginScreen(paddingValues) {
                navController.navigate("home", navOptions {
                    popUpTo("login") { inclusive = true }
                })
            }
        }

        animComposable(route = "home") {
            HomeScreen(paddingValues) { navController.navigate("detail/$it") }
        }

        animComposable(route = "detail/{courseId}") { stack ->
            val courseId = stack.arguments?.getString("courseId") ?: "0"
            DetailScreen(paddingValues, courseId) {
                navController.navigateUp()
            }

        }

        animComposable(route = "favorite") {
            FavoriteScreen(paddingValues)
        }

        animComposable(route = "profile") {
            ProfileScreen(paddingValues) {
                navController.navigate("login", navOptions {
                    popUpTo("home") { inclusive = true }
                })
            }
        }
    }
}


