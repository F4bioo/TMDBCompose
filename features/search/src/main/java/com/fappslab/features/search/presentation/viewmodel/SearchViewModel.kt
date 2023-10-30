package com.fappslab.features.search.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.search.domain.usecase.GetSearchMovieUseCase
import com.fappslab.libraries.arch.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
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
        onState { it.copy(query = queryChanged, shouldShowLoading = true) }
    }

    fun onItemClicked(id: Int) {
        onAction { SearchViewAction.ItemClicked(id) }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { SearchViewAction.TryAgain(pagingItems) }
    }
}
