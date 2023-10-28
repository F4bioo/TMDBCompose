package com.fappslab.features.search.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.search.presentation.component.SearchContent
import com.fappslab.features.search.presentation.viewmodel.SearchViewAction
import com.fappslab.features.search.presentation.viewmodel.SearchViewModel
import com.fappslab.features.search.presentation.viewmodel.SearchViewState
import com.fappslab.libraries.arch.extension.hideKeyboard
import com.fappslab.libraries.arch.viewmodel.compose.OnViewAction
import com.fappslab.libraries.design.component.AppBarView
import com.fappslab.tmdbcompose.features.search.R

@Composable
internal fun SearchScreen(
    navController: NavHostController,
    detailNavigation: DetailNavigation,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val view = LocalView.current

    SearchScaffold(
        state = viewModel.state.collectAsStateWithLifecycle(),
        onSearch = viewModel::getSearchMovie,
        onValueChange = viewModel::onSearch,
        navigateToDetail = viewModel::onItemClicked,
        onTryAgain = viewModel::onTryAgain
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            SearchViewAction.HideKeyboard -> view.hideKeyboard()
            is SearchViewAction.TryAgain -> action.pagingItems.retry()
            is SearchViewAction.ItemClicked -> {
                detailNavigation.navigateToDetail(navController, action.id)
            }
        }
    }
}

@Composable
private fun SearchScaffold(
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
