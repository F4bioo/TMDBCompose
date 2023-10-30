package com.fappslab.features.detail.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.presentation.component.MOVIE_TITLE_VIEW_TAG
import com.fappslab.features.detail.presentation.component.PROGRESS_HEADER_VIEW_TAG
import com.fappslab.features.detail.presentation.component.SYNOPSIS_VIEW_TAG
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
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

    private val robot = DetailScreenRobot(
        initialState,
        DetailViewModel::class,
        composeRule
    ) { viewModel ->
        DetailScreen(
            navController = navController,
            detailNavigation = detailNavigation,
            viewModel = viewModel
        )
    }

    @Test
    fun toolbarTitle_Should_displayTopBarTile_When_screenIsShowing() {
        val expectedTitle = "Movie Detail"

        robot.givenState { initialState.copy(shouldShowLoading = true) }
            .whenLaunch()
            .thenCheck {
                onNodeWithTag(APP_BAR_VIEW_TAG)
                    .onChild()
                    .assertTextEquals(expectedTitle)
            }
    }

    @Test
    fun showLoading_Should_displayProgress_When_screenIsShowing() {
        robot.givenState { initialState.copy(shouldShowLoading = true) }
            .whenLaunch()
            .thenCheck { onNodeWithTag(PROGRESS_HEADER_VIEW_TAG).assertIsDisplayed() }
    }

    @Test
    fun favoriteChecked_Should_BecomeChecked_When_ClickedWhileUnchecked() {
        robot.givenState { initialState.copy(detail = detailDataPreview()) }
            .everyState(
                onInvoke = { it.onFavorite(any()) },
                doReturn = { initialState.copy(isFavoriteChecked = true) }
            )
            .whenLaunch { onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick() }
            .thenCheck { onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).assertIsOn() }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        val expectedTitle = "Avatar: The Way of Water"

        robot.givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck { onNodeWithTag(MOVIE_TITLE_VIEW_TAG).assertTextEquals(expectedTitle) }
    }

    @Test
    fun genres_Should_displayGenres_When_screenIsShowing() {
        val expectedGenres = listOf("Science Fiction", "Adventure", "Action")

        robot.givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck {
                expectedGenres.forEach { genre ->
                    composeRule.onNodeWithText(genre).assertExists()
                }
            }
    }

    @Test
    fun infoGroup_Should_displayCorrectInfo_When_screenIsShowing() {
        val expectedAverageVotesTitle = "Average (votes)"
        val expectedAverageVotes = "7.65"
        val expectedDurationTitle = "Duration"
        val expectedDuration = "192 min."
        val expectedReleaseTitle = "Release"
        val expectedRelease = "2022-12-14"

        robot.givenState { initialState.copy(detail = detailDataPreview()) }
            .whenLaunch()
            .thenCheck { onNodeWithText(expectedAverageVotesTitle).assertExists() }
            .thenCheck { onNodeWithText(expectedAverageVotes).assertExists() }
            .thenCheck { onNodeWithText(expectedDurationTitle).assertExists() }
            .thenCheck { onNodeWithText(expectedDuration).assertExists() }
            .thenCheck { onNodeWithText(expectedReleaseTitle).assertExists() }
            .thenCheck { onNodeWithText(expectedRelease).assertExists() }
    }

    @Test
    fun synopsisView_Should_toggleExpanded_When_textIsClicked() {
        robot.givenState { initialState.copy(detail = detailDataPreview()) }
            .everyState(
                onInvoke = { it.onCollapse(any()) },
                doReturn = { initialState.copy(shouldCollapseText = false) }
            )
            .whenLaunch { onNodeWithTag(SYNOPSIS_VIEW_TAG).performClick() }
            .thenCheck {
                onNodeWithTag(Icons.Default.KeyboardArrowUp.name, useUnmergedTree = true)
                    .assertIsDisplayed()
            }
    }

    @Test
    fun detailContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        robot.givenState { initialState.copy(movies = flowOf(PagingData.from(movies))) }
            .whenLaunch()
            .thenCheck {
                repeat(movies.size) { index ->
                    val id = movies[index].id
                    onNodeWithTag("${COVER_VIEW_TAG}_$id").assertExists()
                }
            }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        // Given
        val movies = moviesDataPreview()
        val movie = movies.first()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        robot.givenState { initialState.copy(movies = flowOf(PagingData.from(movies))) }
            .everyAction(
                onInvoke = { it.onItemClicked(any()) },
                doReturn = { DetailViewAction.ItemClicked(movie.id) }
            )
            .whenLaunch { onNodeWithTag(SYNOPSIS_VIEW_TAG).performClick() }
            .thenCheck {
                onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick()
            }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
