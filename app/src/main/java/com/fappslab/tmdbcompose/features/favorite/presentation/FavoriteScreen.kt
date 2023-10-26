package com.fappslab.tmdbcompose.features.favorite.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fappslab.tmdbcompose.R
import com.fappslab.tmdbcompose.core.arch.viewmodel.compose.OnViewAction
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.core.presentaion.component.AppBarView
import com.fappslab.tmdbcompose.core.presentaion.component.preview.moviesDataPreview
import com.fappslab.tmdbcompose.core.presentaion.navigation.extension.navigateToDetail
import com.fappslab.tmdbcompose.features.favorite.presentation.component.FavoriteContent
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewState

@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    FavoriteScaffold(
        state = viewModel.state.collectAsState(),
        navigateToDetail = viewModel::onItemClicked,
        onCheckedChange = viewModel::onFavorite
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is FavoriteViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
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
fun FavoriteScaffoldPreview() {
    val state = remember { mutableStateOf(FavoriteViewState(movies = moviesDataPreview())) }

    FavoriteScaffold(
        state = state,
        navigateToDetail = {},
        onCheckedChange = {}
    )
}
