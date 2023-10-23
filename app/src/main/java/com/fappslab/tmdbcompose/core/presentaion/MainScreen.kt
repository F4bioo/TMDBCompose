package com.fappslab.tmdbcompose.core.presentaion

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.fappslab.tmdbcompose.core.presentaion.navigation.BottomNavigationBar
import com.fappslab.tmdbcompose.core.presentaion.navigation.NavigationGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = {
            Box(modifier = Modifier.padding(paddingValues = it)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}
