package com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.GetFavoritesUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.IsFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel<FavoriteViewState, FavoriteViewAction>(FavoriteViewState()) {

    init {
        getMovies()
    }

    private fun getMovies() {
        getFavoritesUseCase()
            .onStart { onState { it.copy(shouldShowLoading = true) } }
            .onCompletion { onState { it.copy(shouldShowLoading = false) } }
            .onEach { movies -> onState { it.copy(movies = movies) } }
            .catch { }
            .launchIn(viewModelScope)
    }

    fun onFavorite(movie: Movie) {
        viewModelScope.launch {
            onState { it.copy(shouldShowLoading = true) }
                .runCatching { deleteFavoriteUseCase(movie) }
                .apply { onState { it.copy(shouldShowLoading = false) } }
                .onFailure { }
                .onSuccess { }
        }
    }

    fun onItemClicked(id: Int) {
        onAction { FavoriteViewAction.ItemClicked(id) }
    }
}
