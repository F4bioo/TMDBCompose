package com.fappslab.features.detail.presentation.robot

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.paging.PagingData
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.arch.testing.robot.RobotArrange
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

internal class DetailScreenRobotArrange(
    override val composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: DetailViewModel) -> Unit
) : RobotArrange<DetailScreenRobotAction, DetailViewModel> {

    private val fakeState = MutableStateFlow(DetailViewState())
    private val fakeAction = MutableSharedFlow<DetailViewAction>()
    override val fakeViewModel = mockk<DetailViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    fun showLoadingArrange() {
        fakeState.update { it.copy(shouldShowLoading = true) }
    }

    fun favoriteCheckedArrange() {
        fakeState.update { it.copy(detail = detailDataPreview()) }

        every { fakeViewModel.onFavorite(any()) } answers {
            fakeState.update { it.copy(isFavoriteChecked = true) }
        }
    }

    fun movieTitleArrange() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun genresArrange() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun infoGroupArrange() {
        fakeState.update { it.copy(detail = detailDataPreview()) }
    }

    fun detailContentArrange() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        fakeState.update { it.copy(movies = movies) }
    }

    fun toggleExpandedArrange() {
        fakeState.update { it.copy(detail = detailDataPreview()) }

        every { fakeViewModel.onCollapse(any()) } answers {
            fakeState.update { it.copy(shouldCollapseText = false) }
        }
    }

    fun itemClickedArrange() {
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val movie = movieDataPreview()
        fakeState.update { it.copy(movies = movies) }

        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(DetailViewAction.ItemClicked(movie.id))
        }
    }
}
