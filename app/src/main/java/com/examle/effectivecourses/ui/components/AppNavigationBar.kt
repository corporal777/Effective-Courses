package com.examle.effectivecourses.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.examle.effectivecourses.R
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.BottomItemIndicatorColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun AppBottomBar(navController: NavHostController) {

    val listItems = listOf(
        BottomItem("home", R.drawable.ic_home, "Главная"),
        BottomItem("favorite", R.drawable.ic_favourite, "Избранное"),
        BottomItem("profile", R.drawable.ic_profile, "Аккаунт"),
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""

    var isBottomBarHidden by remember { mutableStateOf(false) }
    isBottomBarHidden = currentRoute == "login"


    if (isBottomBarHidden) return

    CustomNavigationBar {
        listItems.forEach { item ->
            val previousRoute = navController.previousBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = currentRoute == item.route,
                enabled = currentRoute != item.route,
                onClick = {
                    navigateScreen(currentRoute, item.route, navController)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(21.dp),
                        painter = painterResource(item.icon),
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        fontFamily = TextUtils.robotoFont,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = NavigationBarItemColors(
                    selectedTextColor = CourseMoreTextColor,
                    selectedIconColor = CourseMoreTextColor,
                    unselectedTextColor = CourseItemTextColor,
                    unselectedIconColor = CourseItemTextColor,
                    selectedIndicatorColor = BottomItemIndicatorColor,
                    disabledIconColor = CourseMoreTextColor,
                    disabledTextColor = CourseMoreTextColor
                )
            )
        }
    }
}

private fun navigateScreen(currentRoute : String, route: String, navController: NavHostController) {
    if (currentRoute == route) return
    if (!navController.popBackStack(route, false)) {
        val options = if (route == "home") {
            navOptions { popUpTo("home") { inclusive = true } }
        } else null
        navController.navigate(route, options)
    }
}

class BottomItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
private fun CustomNavigationBar(content: @Composable RowScope.() -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((1.5).dp)
                .background(BottomBarLineColor)
        )
        Row(
            modifier = Modifier
                .background(CourseItemColor)
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .height(70.dp)
                //.defaultMinSize(minHeight = 65.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }

}