package com.wuheng.smart.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.graphics.vector.ImageVector
import com.wuheng.smart.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = NavigationRoutes.HOME,
        labelResId = R.string.nav_home,
        icon = Icons.Default.Home
    )

    object Climate : BottomNavItem(
        route = NavigationRoutes.CLIMATE,
        labelResId = R.string.nav_climate,
        icon = Icons.Default.Settings
    )

object Water : BottomNavItem(
        route = NavigationRoutes.WATER,
        labelResId = R.string.nav_water,
        icon = Icons.Default.Build
    )

    object Profile : BottomNavItem(
        route = NavigationRoutes.PROFILE,
        labelResId = R.string.nav_profile,
        icon = Icons.Default.Person
    )

    companion object {
        val items = listOf(Home, Climate, Water, Profile)
    }
}
