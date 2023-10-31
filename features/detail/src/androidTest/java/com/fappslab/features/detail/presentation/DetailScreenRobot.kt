package com.fappslab.features.detail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import com.fappslab.features.detail.presentation.component.SYNOPSIS_VIEW_TAG
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.arch.testing.robot.Robot
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

internal class DetailScreenRobot(
    initialState: DetailViewState,
    override val composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: DetailViewModel) -> Unit
) : Robot<DetailScreenRobotCheck, DetailViewState, DetailViewAction, DetailViewModel>() {

    override val robotCheck = DetailScreenRobotCheck(composeTestRule)
    override val fakeState = MutableStateFlow(initialState)
    override val fakeAction = MutableSharedFlow<DetailViewAction>()
    override val fakeViewModel = mockk<DetailViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    fun showLoadingBehavior() {
        fakeState.update { it.copy(shouldShowLoading = true) }
    }

    fun favoriteCheckedBehavior() {
        fakeState.update { it.copy(detail = detailDataPreview()) }

        every { fakeViewModel.onFavorite(any()) } answers {
            fakeState.update { it.copy(isFavoriteChecked = true) }
        }
    }

    fun favoriteCheckedAction() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick()
    }

    fun movieTitleBehavior() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun genresBehavior() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun infoGroupBehavior() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun detailContentBehavior() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        fakeState.update { it.copy(movies = movies) }
    }

    fun toggleExpandedBehavior() {
        fakeState.update { it.copy(detail = detailDataPreview()) }

        every { fakeViewModel.onCollapse(any()) } answers {
            fakeState.update { it.copy(shouldCollapseText = false) }
        }
    }

    fun toggleExpandedAction() {
        composeTestRule.onNodeWithTag(SYNOPSIS_VIEW_TAG).performClick()
    }

    fun itemClickedBehavior() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val movie = movieDataPreview()
        fakeState.update { it.copy(movies = movies) }

        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(DetailViewAction.ItemClicked(movie.id))
        }
    }

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick()
    }
}
