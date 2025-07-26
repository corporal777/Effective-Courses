package com.examle.effectivecourses.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.extensions.animComposable
import com.examle.effectivecourses.extensions.enterTransition
import com.examle.effectivecourses.extensions.exitTransition
import com.examle.effectivecourses.extensions.popExitTransition
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
        //startDestination = "login",
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        fadeAnimComposable(route = "login") {
            LoginScreen(paddingValues) {
                navController.navigate("home", navOptions {
                    popUpTo("login") { inclusive = true }
                })
            }
        }

        fadeAnimComposable(route = "home") {
            HomeScreen(paddingValues) { navController.navigate("detail/$it") }
        }

        fadeAnimComposable(route = "detail/{courseId}") { stack ->
            val courseId = stack.arguments?.getString("courseId") ?: "0"
            DetailScreen(paddingValues, courseId) { navController.navigateUp() }
        }

        fadeAnimComposable(route = "favorite") {
            FavoriteScreen(paddingValues) { }
        }

        fadeAnimComposable(route = "profile") {
            ProfileScreen(paddingValues) {
                navController.navigate("login", navOptions {
                    popUpTo("home") { inclusive = true }
                })
            }
        }
    }
}

private fun NavGraphBuilder.fadeAnimComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { fadeIn(animationSpec = tween(350, easing = LinearOutSlowInEasing)) },
        exitTransition = { fadeOut(animationSpec = tween(350, easing = LinearOutSlowInEasing)) },
        popExitTransition = { fadeOut(animationSpec = tween(350, easing = LinearOutSlowInEasing)) },
        popEnterTransition = { fadeIn(animationSpec = tween(350, easing = LinearOutSlowInEasing)) },
        content = content
    )
}


