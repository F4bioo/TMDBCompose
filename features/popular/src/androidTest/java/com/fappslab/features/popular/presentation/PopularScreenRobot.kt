package com.fappslab.features.popular.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.features.popular.presentation.viewmodel.PopularViewModel
import com.fappslab.features.popular.presentation.viewmodel.PopularViewState
import com.fappslab.libraries.arch.testing.robot.Robot
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

internal class PopularScreenRobot(
    initialState: PopularViewState,
    override val composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: PopularViewModel) -> Unit
) : Robot<PopularScreenRobotCheck, PopularViewState, PopularViewAction, PopularViewModel>() {

    override val robotCheck = PopularScreenRobotCheck(composeTestRule)
    override val fakeState = MutableStateFlow(initialState)
    override val fakeAction = MutableSharedFlow<PopularViewAction>()
    override val fakeViewModel = mockk<PopularViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    fun detailContentBehavior(movies: List<Movie>) {
        fakeState.update { it.copy(movies = flowOf(PagingData.from(movies))) }
    }

    fun itemClickedBehavior() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val movie = movieDataPreview()
        fakeState.update { it.copy(movies = movies) }

        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(PopularViewAction.ItemClicked(movie.id))
        }
    }

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick()
    }
}
