package com.fappslab.tmdbcompose.features.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.detail.domain.model.Pack
import com.fappslab.tmdbcompose.features.detail.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel<DetailViewState, DetailViewAction>(DetailViewState()) {

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            onState { it.copy(shouldShowLoading = true) }
                .runCatching { getMovieDetailUseCase(id) }
                .apply { onState { it.copy(shouldShowLoading = false) } }
                .onFailure { getMovieDetailFailure(message = it.message) }
                .onSuccess { getMovieDetailSuccess(pack = it) }
        }
    }

    private fun getMovieDetailFailure(message: String?) {
        onState { it.copy(errorMessage = message.orEmpty()) }
    }

    private fun CoroutineScope.getMovieDetailSuccess(pack: Pack) {
        val movies = pack.movies
            .cachedIn(scope = this)
        onState { it.copy(detail = pack.detail, movies = movies) }
    }

    fun onItemClicked(id: Int) {
        onAction { DetailViewAction.ItemClicked(id) }
    }

    fun onCollapse() {
        val current = state.value.shouldCollapseText
        onState { it.copy(shouldCollapseText = current.not()) }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { DetailViewAction.TryAgain(pagingItems) }
    }
}
