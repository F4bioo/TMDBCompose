package com.fappslab.tmdbcompose.core.presentaion.navigation.extension

import androidx.navigation.NavHostController
import com.fappslab.tmdbcompose.core.presentaion.navigation.BottomNavigationItem

fun NavHostController.navigateToDetail(id: Int) {
    navigate(BottomNavigationItem.Detail.withArgs(id))
}
