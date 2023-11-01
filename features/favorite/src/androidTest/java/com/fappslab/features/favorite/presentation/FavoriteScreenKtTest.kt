package com.fappslab.features.favorite.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.libraries.arch.testing.robot.onGiven
import com.fappslab.libraries.arch.testing.robot.onThen
import com.fappslab.libraries.arch.testing.robot.onWhen
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

    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()
    private val screenRobot = FavoriteScreenRobot(composeRule) {
        FavoriteScreen(
            navController = navController,
            detailNavigation = detailNavigation,
            viewModel = it
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun toolbarTitle_Should_displayTopBarTile_When_screenIsShowing() {
        screenRobot
            .onWhen()
            .onThen { checkIfToolbarHasExactlyText() }
    }

    @Test
    fun emptyScreen_Should_displayEmptyScreen_When_favoriteListIsEmpty() {
        val expectedText = "It seems you haven\'t added any movies to your favorites yet."
        screenRobot
            .onWhen()
            .onThen { checkIfHasExactlyText(expectedText) }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        val expectedTitle = "Avatar: The Way of Water"

        screenRobot
            .onGiven { movieTitleArrange() }
            .onWhen()
            .onThen { checkIfHasExactlyText(expectedTitle) }
    }

    @Test
    fun favoriteUnchecked_Should_BecomeUnchecked_When_ClickedWhileChecked() {
        screenRobot
            .onGiven { favoriteUncheckedArrange() }
            .onWhen { favoriteUncheckedAction() }
            .onThen { checkIfFavoriteToggleIsUnchecked() }
    }

    @Test
    fun favoriteContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        screenRobot
            .onGiven { favoriteContentArrange(movies) }
            .onWhen()
            .onThen { checkIfMovieItemsIsPopulated(movies) }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        val movies = moviesDataPreview()
        val movie = movies.first()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        screenRobot
            .onGiven { itemClickedArrange() }
            .onWhen { itemClickedAction() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
