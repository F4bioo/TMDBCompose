package com.fappslab.tmdbcompose.core.presentaion.navigation.extension

import androidx.navigation.NavHostController
import com.fappslab.tmdbcompose.features.detail.navigation.DetailScreenNav

fun NavHostController.navigateToDetail(id: Int) {
    navigate(DetailScreenNav.Detail.withArgs(id))
}
