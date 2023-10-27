package com.fappslab.tmdbcompose.features.search.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.search.domain.usecase.GetSearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase
) : ViewModel<SearchViewState, SearchViewAction>(SearchViewState()) {

    fun getSearchMovie(query: String) {
        val movies = getSearchMovieUseCase(query)
            .catch { }
            .cachedIn(viewModelScope)
        onState { it.copy(movies = movies) }
        onAction { SearchViewAction.HideKeyboard }
    }

    fun onSearch(queryChanged: String) {
        onState { it.copy(query = queryChanged) }
    }

    fun onItemClicked(id: Int) {
        onAction { SearchViewAction.ItemClicked(id) }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { SearchViewAction.TryAgain(pagingItems) }
    }
}
