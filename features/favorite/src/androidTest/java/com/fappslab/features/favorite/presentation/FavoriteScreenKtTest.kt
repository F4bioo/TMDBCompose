package com.fappslab.features.favorite.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.favorite.presentation.component.ITEM_VIEW_TAG
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewState
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class FavoriteScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialState = FavoriteViewState()
    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()
    private val screenRobot = FavoriteScreenRobot(initialState, composeRule) { viewModel ->
        FavoriteScreen(
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
    fun emptyScreen_Should_displayEmptyScreen_When_favoriteListIsEmpty() {
        val expectedText = "It seems you haven\'t added any movies to your favorites yet."
        screenRobot
            .whenLaunch()
            .thenCheck { checkIfHasExactlyText(expectedText) }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        val expectedTitle = "Avatar: The Way of Water"

        screenRobot
            .givenState { initialState.copy(movies = listOf(movieDataPreview())) }
            .whenLaunch()
            .thenCheck { checkIfHasExactlyText(expectedTitle) }
    }

    @Test
    fun favoriteUnchecked_Should_BecomeUnchecked_When_ClickedWhileChecked() {
        screenRobot
            .givenState { initialState.copy(movies = listOf(movieDataPreview())) }
            .everyState(
                onInvoke = { it.onFavorite(any()) },
                doReturn = { initialState.copy(isFavoriteChecked = false) }
            )
            .whenLaunch { onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick() }
            .thenCheck { checkIfFavoriteToggleIsChecked() }
    }

    @Test
    fun favoriteContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        screenRobot
            .givenState { initialState.copy(movies = movies) }
            .whenLaunch()
            .thenCheck { checkIfMovieItemsIsPopulated(movies) }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        val movies = moviesDataPreview()
        val movie = movies.first()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        screenRobot
            .givenState { initialState.copy(movies = movies) }
            .everyAction(
                onInvoke = { it.onItemClicked(any()) },
                doReturn = { FavoriteViewAction.ItemClicked(movie.id) }
            )
            .whenLaunch { onNodeWithTag(testTag = "${ITEM_VIEW_TAG}_${movie.id}").performClick() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
