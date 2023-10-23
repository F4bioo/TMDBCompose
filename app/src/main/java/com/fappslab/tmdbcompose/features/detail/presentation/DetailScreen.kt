package com.fappslab.tmdbcompose.features.detail.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.tmdbcompose.R
import com.fappslab.tmdbcompose.core.arch.viewmodel.compose.OnViewAction
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.core.presentaion.component.AppBarView
import com.fappslab.tmdbcompose.core.presentaion.navigation.extension.navigateToDetail
import com.fappslab.tmdbcompose.features.detail.presentation.component.DetailContent
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewState

@Composable
fun DetailScreen(
    state: State<DetailViewState>,
    navigateToDetail: (id: Int) -> Unit,
    onCollapse: () -> Unit,
    onTryAgain: (pagingItems: LazyPagingItems<Movie>) -> Unit
) {
    val pagingItems = state.value.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBarView(titleRes = R.string.detail_movie)
        },
        content = {
            DetailContent(
                state = state,
                paddingValues = it,
                pagingItems = pagingItems,
                onFavorite = {},
                onItemClick = navigateToDetail,
                onCollapse = onCollapse,
                onRetry = {
                    onTryAgain(pagingItems)
                }
            )
        }
    )
}

@Composable
fun Detail(id: Int, navHostController: NavHostController) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val state = viewModel.state.collectAsState()

    viewModel.getMovieDetail(id)

    DetailScreen(
        state = state,
        navigateToDetail = viewModel::onItemClicked,
        onCollapse = viewModel::onCollapse,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel = viewModel) { action ->
        when (action) {
            is DetailViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
            is DetailViewAction.TryAgain -> action.pagingItems.retry()
        }
    }
}
