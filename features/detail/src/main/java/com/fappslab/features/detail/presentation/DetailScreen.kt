package com.fappslab.features.detail.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.presentation.component.DetailContent
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.arch.viewmodel.compose.OnViewAction
import com.fappslab.libraries.design.component.AppBarView
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.features.detail.R

@Composable
internal fun DetailScreen(
    navController: NavHostController,
    detailNavigation: DetailNavigation,
    viewModel: DetailViewModel = hiltViewModel()
) {
    viewModel.getMovieDetail()

    DetailScaffold(
        state = viewModel.state.collectAsStateWithLifecycle(),
        navigateToDetail = viewModel::onItemClicked,
        onCollapse = viewModel::onCollapse,
        onFavorite = viewModel::onFavorite,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is DetailViewAction.TryAgain -> action.pagingItems.retry()
            is DetailViewAction.ItemClicked -> {
                detailNavigation.navigateToDetail(navController, action.id)
            }
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
