package com.fappslab.libraries.popular.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import com.fappslab.libraries.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.libraries.popular.presentation.viewmodel.PopularViewState
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
internal class PopularScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialState = PopularViewState()
    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()
    private val screenRobot = PopularScreenRobot(initialState, composeRule) { viewModel ->
        PopularScreen(
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
                doReturn = { PopularViewAction.ItemClicked(movie.id) }
            )
            .whenLaunch { onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
