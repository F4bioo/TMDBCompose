package com.fappslab.tmdbcompose.features.popular.presentation

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
import com.fappslab.tmdbcompose.features.popular.presentation.component.MovieContent
import com.fappslab.tmdbcompose.features.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.tmdbcompose.features.popular.presentation.viewmodel.PopularViewModel
import com.fappslab.tmdbcompose.features.popular.presentation.viewmodel.PopularViewState

@Composable
fun PopularScreen(
    navHostController: NavHostController,
    viewModel: PopularViewModel = hiltViewModel()
) {
    PopularScaffold(
        state = viewModel.state.collectAsState(),
        navigateToDetail = viewModel::onItemClicked,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is PopularViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
            is PopularViewAction.TryAgain -> action.pagingItems.retry()
        }
    }
}


@Composable
private fun PopularScaffold(
    state: State<PopularViewState>,
    navigateToDetail: (id: Int) -> Unit,
    onTryAgain: (pagingItems: LazyPagingItems<Movie>) -> Unit
) {
    val pagingItems = state.value.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBarView(titleRes = R.string.popular_movies)
        },
        content = {
            MovieContent(
                paddingValues = it,
                pagingItems = pagingItems,
                onItemClick = navigateToDetail,
                onRetry = {
                    onTryAgain(pagingItems)
                }
            )
        }
    )
}
