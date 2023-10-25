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
import com.fappslab.tmdbcompose.core.presentaion.navigation.extension.navigateToDetail
import com.fappslab.tmdbcompose.features.favorite.presentation.component.FavoriteContent
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.tmdbcompose.features.favorite.presentation.viewmodel.FavoriteViewState

@Composable
fun FavoriteScreen(
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

@Composable
fun Favorite(navHostController: NavHostController) {
    val viewModel = hiltViewModel<FavoriteViewModel>()
    val state = viewModel.state.collectAsState()

    FavoriteScreen(
        state = state,
        navigateToDetail = viewModel::onItemClicked,
        onCheckedChange = viewModel::onFavorite
    )

    OnViewAction(viewModel) { action ->
        when (action) {
            is FavoriteViewAction.ItemClicked -> navHostController.navigateToDetail(action.id)
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    fun movie(id: Int) = Movie(
        id = id,
        title = "Avatar: The Way of Water",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        voteAverage = 7.65,
    )

    val state = remember {
        mutableStateOf(
            FavoriteViewState(
                movies = listOf(movie(id = 1), movie(id = 2))
            )
        )
    }

    FavoriteScreen(
        state = state,
        navigateToDetail = {},
        onCheckedChange = {}
    )
}
