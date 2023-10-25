package com.fappslab.tmdbcompose.features.popular.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.popular.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel<PopularViewState, PopularViewAction>(PopularViewState()) {

    init {
        getMovies()
    }

    private fun getMovies() {
        val movies = getMoviesUseCase()
            .catch { }
            .cachedIn(viewModelScope)
        onState { it.copy(movies = movies) }
    }

    fun onItemClicked(id: Int) {
        onAction { PopularViewAction.ItemClicked(id) }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { PopularViewAction.TryAgain(pagingItems) }
    }
}
