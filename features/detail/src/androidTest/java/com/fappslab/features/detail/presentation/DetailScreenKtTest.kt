package com.fappslab.features.detail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.presentation.component.SYNOPSIS_VIEW_TAG
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class DetailScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialState = DetailViewState()
    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()
    private val screenRobot = DetailScreenRobot(initialState, composeRule) { viewModel ->
        DetailScreen(
            navController = navController,
            detailNavigation = detailNavigation,
            viewModel = viewModel
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun toolbarTitle_Should_displayTopBarTile_When_screenIsShowing() {
        screenRobot
            .whenLaunch()
            .thenCheck { checkIfToolbarHasExactlyText() }
    }

    @Test
    fun showLoading_Should_displayProgress_When_screenIsShowing() {
        screenRobot
            .givenState { initialState.copy(shouldShowLoading = true) }
            .whenLaunch()
            .thenCheck { checkIfLoadingIsDisplayed() }
    }

    @Test
    fun favoriteChecked_Should_BecomeChecked_When_ClickedWhileUnchecked() {
        screenRobot
            .givenState { initialState.copy(detail = detailDataPreview()) }
            .everyState(
                onInvoke = { it.onFavorite(any()) },
                doReturn = { initialState.copy(isFavoriteChecked = true) }
            )
            .whenLaunch { onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick() }
            .thenCheck { checkIfFavoriteToggleIsChecked() }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        screenRobot
            .givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck { checkIfMovieTitleHasExactlyText() }
    }

    @Test
    fun genres_Should_displayGenres_When_screenIsShowing() {
        screenRobot
            .givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck { checkIfHasExactlyGenreList() }
    }

    @Test
    fun infoGroup_Should_displayCorrectInfo_When_screenIsShowing() {
        val expectedAverageVotesTitle = "Average (votes)"
        val expectedAverageVotes = "7.65"
        val expectedDurationTitle = "Duration"
        val expectedDuration = "192 min."
        val expectedReleaseTitle = "Release"
        val expectedRelease = "2022-12-14"

        screenRobot.givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck { checkIfHasExactlyText(expectedAverageVotesTitle) }
            .thenCheck { checkIfHasExactlyText(expectedAverageVotes) }
            .thenCheck { checkIfHasExactlyText(expectedDurationTitle) }
            .thenCheck { checkIfHasExactlyText(expectedDuration) }
            .thenCheck { checkIfHasExactlyText(expectedReleaseTitle) }
            .thenCheck { checkIfHasExactlyText(expectedRelease) }
    }

    @Test
    fun synopsisView_Should_toggleExpanded_When_textIsClicked() {
        screenRobot
            .givenState { initialState.copy(detail = detailDataPreview()) }
            .everyState(
                onInvoke = { it.onCollapse(any()) },
                doReturn = { initialState.copy(shouldCollapseText = false) }
            )
            .whenLaunch { onNodeWithTag(SYNOPSIS_VIEW_TAG).performClick() }
            .thenCheck { checkIfArrowIconIsChanged() }
    }

    @Test
    fun detailContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        screenRobot
            .givenState { initialState.copy(movies = flowOf(PagingData.from(movies))) }
            .whenLaunch()
            .thenCheck { checkIfMovieItemsIsPopulated(movies) }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        val movies = moviesDataPreview()
        val movie = movies.first()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        screenRobot
            .givenState { initialState.copy(movies = flowOf(PagingData.from(movies))) }
            .everyAction(
                onInvoke = { it.onItemClicked(any()) },
                doReturn = { DetailViewAction.ItemClicked(movie.id) }
            )
            .whenLaunch { onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
