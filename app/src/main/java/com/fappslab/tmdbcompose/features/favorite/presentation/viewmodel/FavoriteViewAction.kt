package com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel

sealed class FavoriteViewAction {
    data class ItemClicked(val id: Int) : FavoriteViewAction()
}
