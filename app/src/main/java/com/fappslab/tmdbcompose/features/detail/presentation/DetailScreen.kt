package com.fappslab.tmdbcompose.features.detail.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.tmdbcompose.R
import com.fappslab.tmdbcompose.core.arch.viewmodel.compose.OnViewAction
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.core.presentaion.component.AppBarView
import com.fappslab.tmdbcompose.core.presentaion.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.core.presentaion.navigation.extension.navigateToDetail
import com.fappslab.tmdbcompose.features.detail.presentation.component.DetailContent
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewState

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    viewModel.getMovieDetail()

    DetailScaffold(
        state = viewModel.state.collectAsState(),
        navigateToDetail = viewModel::onItemClicked,
        onCollapse = viewModel::onCollapse,
        onFavorite = viewModel::onFavorite,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is DetailViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
            is DetailViewAction.TryAgain -> action.pagingItems.retry()
        }
    }
}

@Composable
private fun DetailScaffold(
    state: State<DetailViewState>,
    navigateToDetail: (id: Int) -> Unit,
    onCollapse: () -> Unit,
    onFavorite: (movie: Movie) -> Unit,
    onTryAgain: (pagingItems: LazyPagingItems<Movie>) -> Unit
) {
    val pagingItems = state.value.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBarView(titleRes = R.string.detail_movie)
        },
        content = {
            DetailContent(
                state = state.value,
                paddingValues = it,
                pagingItems = pagingItems,
                onFavorite = onFavorite,
                onItemClick = navigateToDetail,
                onCollapse = onCollapse,
                onRetry = {
                    onTryAgain(pagingItems)
                }
            )
        }
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    val state = remember { mutableStateOf(DetailViewState(detail = detailDataPreview())) }

    DetailScaffold(
        state = state,
        navigateToDetail = {},
        onCollapse = {},
        onFavorite = {},
        onTryAgain = {},
    )
}
