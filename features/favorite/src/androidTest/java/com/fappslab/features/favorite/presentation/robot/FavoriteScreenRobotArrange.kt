package com.fappslab.features.favorite.presentation.robot

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewState
import com.fappslab.libraries.arch.testing.robot.RobotArrange
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class FavoriteScreenRobotArrange(
    composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: FavoriteViewModel) -> Unit
) : RobotArrange<FavoriteScreenRobotAction, FavoriteScreenRobotCheck, FavoriteViewState, FavoriteViewAction, FavoriteViewModel>() {

    override val robotAction = FavoriteScreenRobotAction(composeTestRule)
    override val robotCheck = FavoriteScreenRobotCheck(composeTestRule)
    override val fakeState = MutableStateFlow(FavoriteViewState())
    override val fakeAction = MutableSharedFlow<FavoriteViewAction>()
    override val fakeViewModel = mockk<FavoriteViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    fun movieTitleArrange() {
        fakeState.update { it.copy(movies = listOf(movieDataPreview())) }
    }

    fun favoriteUncheckedArrange() {
        fakeState.update { it.copy(movies = listOf(movieDataPreview())) }

        every { fakeViewModel.onFavorite(any()) } answers {
            fakeState.update { it.copy(isFavoriteChecked = false) }
        }
    }

    fun favoriteContentArrange(movies: List<Movie>) {
        fakeState.update { it.copy(movies = movies) }
    }

    fun itemClickedArrange() {
        val movies = moviesDataPreview()
        val movie = movieDataPreview()
        fakeState.update { it.copy(movies = movies) }

        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(FavoriteViewAction.ItemClicked(movie.id))
        }
    }
}
