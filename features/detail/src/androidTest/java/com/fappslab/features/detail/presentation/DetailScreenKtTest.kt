package com.fappslab.features.detail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.arch.testing.robot.onGiven
import com.fappslab.libraries.arch.testing.robot.onThen
import com.fappslab.libraries.arch.testing.robot.onWhen
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
            .onWhen()
            .onThen { checkIfToolbarHasExactlyText() }
    }

    @Test
    fun showLoading_Should_displayProgress_When_screenIsShowing() {
        screenRobot
            .onGiven { showLoadingBehavior() }
            .onWhen()
            .onThen { checkIfLoadingIsDisplayed() }
    }

    @Test
    fun favoriteChecked_Should_BecomeChecked_When_ClickedWhileUnchecked() {
        screenRobot
            .onGiven { favoriteCheckedBehavior() }
            .onWhen { favoriteCheckedAction() }
            .onThen { checkIfFavoriteToggleIsChecked() }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        screenRobot
            .onGiven { movieTitleBehavior() }
            .onWhen()
            .onThen { checkIfMovieTitleHasExactlyText() }
    }

    @Test
    fun genres_Should_displayGenres_When_screenIsShowing() {
        screenRobot
            .onGiven { genresBehavior() }
            .onWhen()
            .onThen { checkIfHasExactlyGenreList() }
    }

    @Test
    fun infoGroup_Should_displayCorrectInfo_When_screenIsShowing() {
        screenRobot
            .onGiven { infoGroupBehavior() }
            .onWhen()
            .onThen { checkIfHasExactlyInfoGroupTexts() }
    }

    @Test
    fun synopsisView_Should_toggleExpanded_When_textIsClicked() {
        screenRobot
            .onGiven { toggleExpandedBehavior() }
            .onWhen { toggleExpandedAction() }
            .onThen { checkIfArrowIconIsChanged() }
    }

    @Test
    fun detailContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        screenRobot
            .onGiven { detailContentBehavior() }
            .onWhen()
            .onThen { checkIfMovieItemsIsPopulated(movies) }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        val movie = movieDataPreview()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        screenRobot
            .onGiven { itemClickedBehavior() }
            .onWhen { itemClickedAction() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
