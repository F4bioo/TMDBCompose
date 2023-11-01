package com.fappslab.features.favorite.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.favorite.presentation.component.ITEM_VIEW_TAG
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewState
import com.fappslab.libraries.arch.testing.robot.Robot
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class FavoriteScreenRobot(
    override val composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: FavoriteViewModel) -> Unit
) : Robot<FavoriteScreenRobotCheck, FavoriteViewState, FavoriteViewAction, FavoriteViewModel>() {

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

    fun favoriteUncheckedAction() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick()
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

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${ITEM_VIEW_TAG}_${movie.id}").performClick()
    }
}
