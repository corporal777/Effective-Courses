package com.examle.effectivecourses.extensions

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.core.bundle.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.examle.effectivecourses.R
import com.examle.effectivecourses.ui.theme.PurpleGrey40

inline fun <T> Iterable<T>.findItem(predicate: (T) -> Boolean): Pair<Int, T?> {
    for ((index, item) in this.withIndex()) {
        if (predicate(item))
            return Pair(index, item)
    }
    return Pair(-1, null)
}

fun horizontalGradientBrush(colors: List<Color>): Brush {
    return Brush.horizontalGradient(colors)
}

fun verticalGradientBrush(colors: List<Color>): Brush {
    return Brush.verticalGradient(colors)
}

fun NavGraphBuilder.animComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = enterTransition,
        popExitTransition = popExitTransition,
        //exitTransition = exitTransition,
        exitTransition = null,
        //popEnterTransition = popEnterTransition,
        popEnterTransition = null,
        content = content
    )
}

val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideInHorizontally(
        initialOffsetX = { (it / 7.5).toInt() },
        animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing)
    ) + fadeIn(animationSpec = tween(250))
}

val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutHorizontally(
        targetOffsetX = { (it / 7.5).toInt() },
        animationSpec = tween(durationMillis = 280, easing = LinearOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(250))
}

val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutHorizontally(
        targetOffsetX = { (-it / 7).toInt() },
        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(300, easing = LinearOutSlowInEasing))
}

val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideInHorizontally(
        initialOffsetX = { -it / 5 },
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    ) + fadeIn(animationSpec = tween(280, easing = LinearOutSlowInEasing))
}

fun Modifier.clickable(
    rippleColor: Color? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["rippleColor"] = rippleColor
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        onClick = onClick,
        indication = rippleColor?.let {
            ripple(
                color = it
            )
        } ?: LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    )
}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val nodeId = graph.findNode(route = route)?.id
    if (nodeId != null) {
        navigate(nodeId, args, navOptions, navigatorExtras)
    }
}

@Composable
fun LaunchedAnimation(onAnim: (alpha: Float) -> Unit) {
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(300)
        ) { value, vel ->
            onAnim.invoke(value)
        }
    }
}

fun showCustomTabsBrowser(context: Context, url: String) {
    val pageUrl =
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            Uri.parse("https://$url")
        else Uri.parse(url)
    try {
        val customTabsIntent = CustomTabsIntent.Builder().apply {
            setStartAnimations(context, R.anim.browser_popup_enter, android.R.anim.fade_out)
            setExitAnimations(context, android.R.anim.fade_in, R.anim.browser_popup_exit)
        }.build()
        customTabsIntent.launchUrl(context, pageUrl)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Не удалось открыть страницу", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Modifier.shimmerLoading(
    durationMillis: Int = 1000,
): Modifier {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )

    return drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    PurpleGrey40.copy(alpha = 0.2f),
                    PurpleGrey40.copy(alpha = 1.0f),
                    PurpleGrey40.copy(alpha = 0.2f),
                ),
                start = Offset(x = translateAnimation, y = translateAnimation),
                end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
            )
        )
    }
}


@Composable
fun DisposableAction(key: Any, onDispose: ((dispose : Boolean) -> Unit?)? = null, onChanged: () -> Unit){
    DisposableEffect(key) {
        onChanged.invoke()
        onDispose { onDispose?.invoke(true) }
    }
}


