package com.fappslab.features.popular.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.popular.domain.usecase.GetMoviesUseCase
import com.fappslab.libraries.arch.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
internal class PopularViewModel @Inject constructor(
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
