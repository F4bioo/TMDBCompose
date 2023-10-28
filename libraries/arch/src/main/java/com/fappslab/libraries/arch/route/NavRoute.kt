package com.fappslab.libraries.arch.route

import androidx.compose.ui.graphics.vector.ImageVector

interface NavRoute {
    val titleRes: Int
    val icon: ImageVector
    val route: String
}
