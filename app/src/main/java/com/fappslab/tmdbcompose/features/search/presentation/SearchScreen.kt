package com.fappslab.tmdbcompose.features.search.presentation

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
import com.fappslab.tmdbcompose.features.search.presentation.component.SearchContent
import com.fappslab.tmdbcompose.features.search.presentation.viewmodel.SearchViewAction
import com.fappslab.tmdbcompose.features.search.presentation.viewmodel.SearchViewModel
import com.fappslab.tmdbcompose.features.search.presentation.viewmodel.SearchViewState

@Composable
fun SearchScreen(
    state: State<SearchViewState>,
    onSearch: (query: String) -> Unit,
    onValueChange: (queryChanged: String) -> Unit,
    navigateToDetail: (id: Int) -> Unit,
    onTryAgain: (pagingItems: LazyPagingItems<Movie>) -> Unit
) {
    val pagingItems = state.value.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppBarView(titleRes = R.string.search_movies)
        },
        content = {
            SearchContent(
                state = state.value,
                paddingValues = it,
                pagingItems = pagingItems,
                onSearch = onSearch,
                onValueChange = onValueChange,
                onItemClick = navigateToDetail,
                onRetry = {
                    onTryAgain(pagingItems)
                }
            )
        }
    )
}

@Composable
fun Search(navHostController: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val state = viewModel.state.collectAsState()

    SearchScreen(
        state = state,
        onSearch = viewModel::getSearchMovie,
        onValueChange = viewModel::onSearch,
        navigateToDetail = viewModel::onItemClicked,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is SearchViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
            is SearchViewAction.TryAgain -> action.pagingItems.retry()
        }
    }
}
