package com.fappslab.tmdbcompose.features.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.detail.domain.model.Pack
import com.fappslab.tmdbcompose.features.detail.domain.usecase.GetMovieDetailUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.IsFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel<DetailViewState, DetailViewAction>(DetailViewState()) {

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            onState { it.copy(shouldShowLoading = true) }
                .runCatching {
                    val detail = async { getMovieDetailUseCase(id) }
                    val pack = detail.await()
                    val isFavorite = async { isFavoriteUseCase(pack.detail.id) }
                    pack to isFavorite.await()
                }
                .apply { onState { it.copy(shouldShowLoading = false) } }
                .onFailure { getMovieDetailFailure(message = it.message) }
                .onSuccess { getMovieDetailSuccess(successPair = it) }
        }
    }

    private fun getMovieDetailFailure(message: String?) {
        onState { it.copy(errorMessage = message.orEmpty()) }
    }

    private fun CoroutineScope.getMovieDetailSuccess(successPair: Pair<Pack, Boolean>) {
        val (pack, isChecked) = successPair

        val movies = pack.movies
            .catch { }
            .cachedIn(scope = this)
        onState { it.copy(detail = pack.detail, movies = movies, isFavoriteChecked = isChecked) }
    }

    fun onItemClicked(id: Int) {
        onAction { DetailViewAction.ItemClicked(id) }
    }

    fun onCollapse() {
        val current = state.value.shouldCollapseText
        onState { it.copy(shouldCollapseText = current.not()) }
    }

    fun onFavorite(movie: Movie) {
        val isFavoriteChecked = state.value.isFavoriteChecked

        viewModelScope.launch {
            onState { it.copy(shouldShowLoading = true) }
                .runCatching {
                    if (isFavoriteChecked) {
                        deleteFavoriteUseCase(movie)
                    } else setFavoriteUseCase(movie)
                }
                .apply { onState { it.copy(shouldShowLoading = false) } }
                .onFailure { }
                .onSuccess { onState { it.copy(isFavoriteChecked = isFavoriteChecked.not()) } }
        }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { DetailViewAction.TryAgain(pagingItems) }
    }
}
