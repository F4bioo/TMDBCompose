package com.fappslab.features.popular.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.popular.presentation.component.MovieContent
import com.fappslab.features.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.features.popular.presentation.viewmodel.PopularViewModel
import com.fappslab.features.popular.presentation.viewmodel.PopularViewState
import com.fappslab.libraries.arch.viewmodel.compose.OnViewAction
import com.fappslab.libraries.design.component.AppBarView
import com.fappslab.tmdbcompose.features.popular.R

@Composable
internal fun PopularScreen(
    navController: NavHostController,
    detailNavigation: DetailNavigation,
    viewModel: PopularViewModel = hiltViewModel()
) {
    PopularScaffold(
        state = viewModel.state.collectAsStateWithLifecycle(),
        navigateToDetail = viewModel::onItemClicked,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is PopularViewAction.TryAgain -> action.pagingItems.retry()
            is PopularViewAction.ItemClicked -> {
                detailNavigation.navigateToDetail(navController, action.id)
            }
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
