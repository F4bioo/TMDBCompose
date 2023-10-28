package com.fappslab.features.favorite.presentation.viewmodel

internal sealed class FavoriteViewAction {
    data class ItemClicked(val id: Int) : FavoriteViewAction()
}
