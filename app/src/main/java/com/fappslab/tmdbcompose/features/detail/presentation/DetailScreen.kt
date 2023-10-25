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
import com.fappslab.tmdbcompose.core.domain.model.Detail
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

@Composable
fun Detail(id: Int, navHostController: NavHostController) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val state = viewModel.state.collectAsState()

    viewModel.getMovieDetail(id)

    DetailScreen(
        state = state,
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

@Preview
@Composable
fun DetailScreenPreview() {
    val detail = Detail(
        id = 1,
        title = "Avatar: The Way of Water",
        genres = listOf("Science Fiction", "Adventure", "Action"),
        overview = "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        releaseDate = "2022-12-14",
        voteAverage = 7.65,
        duration = 192,
        voteCount = 9977
    )

    val state = remember { mutableStateOf(DetailViewState(detail = detail)) }

    DetailScreen(
        state = state,
        navigateToDetail = {},
        onCollapse = {},
        onFavorite = {},
        onTryAgain = {},
    )
}
