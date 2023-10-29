package com.fappslab.features.favorite.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.fappslab.core.domain.model.Movie
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.favorite.presentation.component.FavoriteContent
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewState
import com.fappslab.libraries.arch.viewmodel.compose.OnViewAction
import com.fappslab.libraries.design.component.AppBarView
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import com.fappslab.tmdbcompose.features.favorites.R

@Composable
internal fun FavoriteScreen(
    navController: NavHostController,
    detailNavigation: DetailNavigation,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    FavoriteScaffold(
        state = viewModel.state.collectAsStateWithLifecycle(),
        navigateToDetail = viewModel::onItemClicked,
        onCheckedChange = viewModel::onFavorite
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is FavoriteViewAction.ItemClicked -> {
                detailNavigation.navigateToDetail(navController, action.id)
            }
        }
    }
}

@Composable
private fun FavoriteScaffold(
    state: State<FavoriteViewState>,
    navigateToDetail: (id: Int) -> Unit,
    onCheckedChange: (movie: Movie) -> Unit
) {
    Scaffold(
        topBar = {
            AppBarView(titleRes = R.string.favorite_movies)
        },
        content = {
            FavoriteContent(
                paddingValues = it,
                movies = state.value.movies,
                isFavorite = state.value.isFavoriteChecked,
                onCheckedChange = onCheckedChange,
                onItemClick = navigateToDetail
            )
        }
    )
}

@Preview
@Composable
private fun FavoriteScaffoldPreview() {
    val state = remember { mutableStateOf(FavoriteViewState(movies = moviesDataPreview())) }

    FavoriteScaffold(
        state = state,
        navigateToDetail = {},
        onCheckedChange = {}
    )
}
