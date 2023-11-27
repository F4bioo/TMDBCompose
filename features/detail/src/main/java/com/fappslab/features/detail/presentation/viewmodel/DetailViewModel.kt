package com.fappslab.features.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.navigation.DETAILS_ID_ARGS_KEY
import com.fappslab.features.detail.domain.model.Pack
import com.fappslab.features.detail.domain.usecase.provider.DetailUseCaseProvider
import com.fappslab.libraries.arch.extension.orZero
import com.fappslab.libraries.arch.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val provider: DetailUseCaseProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel<DetailViewState, DetailViewAction>(DetailViewState()) {

    private val id
        get() = savedStateHandle.get<Int>(DETAILS_ID_ARGS_KEY)
            .orZero()

    /**
     * Why CoroutineScope here?
     *
     * The use of `coroutineScope` in this context is crucial for managing exceptions effectively within asynchronous operations:
     *   - It ensures that exceptions thrown by any of the `async` tasks are captured and handled within the `runCatching` block.
     *     This prevents exceptions from leaking out and terminating the coroutine unexpectedly.
     *   - By grouping multiple asynchronous operations (`async` tasks), `coroutineScope` ensures they are treated as a single unit.
     *     This is particularly useful when these operations are logically connected and their outcomes are interdependent.
     *   - Additionally, `coroutineScope` maintains the parent coroutine's context, ensuring consistent behavior and exception handling
     *     across different parts of the coroutine hierarchy.
     */
    fun getMovieDetail() {
        viewModelScope.launch {
            onState { it.copy(shouldShowLoading = true) }
                .runCatching {
                    coroutineScope {
                        val detailDeferred = async { provider.getMovieDetailUseCase(id) }
                        val isFavoriteDeferred = async { provider.isFavoriteUseCase(id) }
                        val detail = detailDeferred.await()
                        val isFavorite = isFavoriteDeferred.await()
                        detail to isFavorite
                    }
                }
                .onFailure { getMovieDetailFailure(message = it.message) }
                .onSuccess { getMovieDetailSuccess(successPair = it) }
                .also { onState { it.copy(shouldShowLoading = false) } }
        }
    }

    private fun getMovieDetailFailure(message: String?) {
        onState { it.copy(errorMessage = message.orEmpty()) }
    }

    private fun getMovieDetailSuccess(successPair: Pair<Pack, Boolean>) {
        val (pack) = successPair
        val movies = pack.movies
            .catch { }
            .cachedIn(viewModelScope)
        onState { it.setSuccessState(movies, successPair) }
    }

    fun onItemClicked(id: Int) {
        onAction { DetailViewAction.ItemClicked(id) }
    }

    fun onCollapse(shouldCollapse: Boolean) {
        onState { it.copy(shouldCollapseText = shouldCollapse) }
    }

    fun onFavorite(movie: Movie) {
        viewModelScope.launch {
            val isFavoriteChecked = state.value.isFavoriteChecked
            onState { it.copy(shouldShowLoading = true) }
                .runCatching {
                    if (isFavoriteChecked) {
                        provider.deleteFavoriteUseCase(movie)
                    } else provider.setFavoriteUseCase(movie)
                }
                .onFailure { }
                .onSuccess { onState { it.copy(isFavoriteChecked = isFavoriteChecked.not()) } }
                .also { onState { it.copy(shouldShowLoading = false) } }
        }
    }

    fun onTryAgain(pagingItems: LazyPagingItems<Movie>) {
        onAction { DetailViewAction.TryAgain(pagingItems) }
    }
}
