package com.fappslab.tmdbcompose.features.detail.navigation

import com.fappslab.tmdbcompose.core.data.common.constant.Util

sealed class DetailScreenNav(val route: String) {
    object Detail : DetailScreenNav(
        route = "detail_screen/{${Util.ID_ARGS_KEY}}"
    ) {
        fun withArgs(id: Int): String {
            return "detail_screen/$id"
        }
    }
}
