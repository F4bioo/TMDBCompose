package com.fappslab.features.popular.presentation.robot

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.features.popular.presentation.viewmodel.PopularViewModel
import com.fappslab.features.popular.presentation.viewmodel.PopularViewState
import com.fappslab.libraries.arch.testing.robot.RobotArrange
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

internal class PopularScreenRobotArrange(
    override val composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: PopularViewModel) -> Unit
) : RobotArrange<PopularScreenRobotAction, PopularViewModel> {

    private val fakeState = MutableStateFlow(PopularViewState())
    private val fakeAction = MutableSharedFlow<PopularViewAction>()
    override val fakeViewModel = mockk<PopularViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    fun detailContentArrange(movies: List<Movie>) {
        fakeState.update { it.copy(movies = flowOf(PagingData.from(movies))) }
    }

    fun itemClickedArrange() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val movie = movieDataPreview()
        fakeState.update { it.copy(movies = movies) }

        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(PopularViewAction.ItemClicked(movie.id))
        }
    }
}
