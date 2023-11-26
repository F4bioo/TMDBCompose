package com.fappslab.features.favorite.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.features.favorite.domain.GetFavoritesUseCase
import com.fappslab.libraries.arch.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel<FavoriteViewState, FavoriteViewAction>(FavoriteViewState()) {

    init {
        getFavorites()
    }

    private fun getFavorites() {
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
                .onFailure { }
                .also { onState { it.copy(shouldShowLoading = false) } }
        }
    }

    fun onItemClicked(id: Int) {
        onAction { FavoriteViewAction.ItemClicked(id) }
    }
}
