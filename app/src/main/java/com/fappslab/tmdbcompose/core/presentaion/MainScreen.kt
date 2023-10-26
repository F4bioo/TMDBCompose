package com.fappslab.tmdbcompose.core.presentaion

import android.view.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import com.fappslab.tmdbcompose.core.data.common.extension.isNotNull
import com.fappslab.tmdbcompose.core.presentaion.navigation.BottomNavigationBar
import com.fappslab.tmdbcompose.core.presentaion.navigation.NavigationGraph
import com.fappslab.tmdbcompose.core.presentaion.navigation.currentRout
import com.fappslab.tmdbcompose.features.detail.navigation.DetailScreenNav

@Composable
fun MainScreen(
    window: Window? = null,
    color: Color = Color.Black,
    navController: NavHostController
) {
    if (window.isNotNull()) {
        LaunchedEffect(key1 = true) {
            window?.statusBarColor = color.toArgb()
        }
    }
    Scaffold(
        content = {
            Box(
                modifier = Modifier.padding(paddingValues = it)
            ) {
                NavigationGraph(navController = navController)
            }
        },
        bottomBar = {
            if (currentRout(navController = navController) != DetailScreenNav.Detail.route) {
                BottomNavigationBar(navController = navController)
            }
        }
    )
}
