package com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel

import com.fappslab.tmdbcompose.core.domain.model.Movie

data class FavoriteViewState(
    val isFavoriteChecked: Boolean = true,
    val shouldShowLoading: Boolean = false,
    val movies: List<Movie> = emptyList()
)
