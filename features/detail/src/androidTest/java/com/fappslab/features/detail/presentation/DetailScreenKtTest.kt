package com.fappslab.features.detail.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.presentation.robot.DetailScreenRobotArrange
import com.fappslab.libraries.arch.testing.robot.givenArrange
import com.fappslab.libraries.arch.testing.robot.thenCheck
import com.fappslab.libraries.arch.testing.robot.whenAction
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

    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()
    private val robotScreen = DetailScreenRobotArrange(composeRule) {
        DetailScreen(
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
        robotScreen
            .givenArrange()
            .whenAction()
            .thenCheck { checkIfToolbarHasExactlyText() }
    }

    @Test
    fun showLoading_Should_displayProgress_When_screenIsShowing() {
        robotScreen
            .givenArrange { showLoadingArrange() }
            .whenAction()
            .thenCheck { checkIfLoadingIsDisplayed() }
    }

    @Test
    fun favoriteChecked_Should_BecomeChecked_When_ClickedWhileUnchecked() {
        robotScreen
            .givenArrange {
                favoriteCheckedArrange()
            }
            .whenAction { favoriteCheckedAction() }
            .thenCheck { checkIfFavoriteToggleIsChecked() }
    }

    @Test
    fun movieTitle_Should_displayMovieTitle_When_screenIsShowing() {
        robotScreen
            .givenArrange { movieTitleArrange() }
            .whenAction()
            .thenCheck { checkIfMovieTitleHasExactlyText() }
    }

    @Test
    fun genres_Should_displayGenres_When_screenIsShowing() {
        robotScreen
            .givenArrange { genresArrange() }
            .whenAction()
            .thenCheck { checkIfHasExactlyGenreList() }
    }

    @Test
    fun infoGroup_Should_displayCorrectInfo_When_screenIsShowing() {
        robotScreen
            .givenArrange { infoGroupArrange() }
            .whenAction()
            .thenCheck { checkIfHasExactlyInfoGroupTexts() }
    }

    @Test
    fun synopsisView_Should_toggleExpanded_When_textIsClicked() {
        robotScreen
            .givenArrange { toggleExpandedArrange() }
            .whenAction { toggleExpandedAction() }
            .thenCheck { checkIfArrowIconIsChanged() }
    }

    @Test
    fun detailContent_Should_PopulateMovieList_When_ThereAreMovies() {
        val movies = moviesDataPreview()

        robotScreen
            .givenArrange { detailContentArrange() }
            .whenAction()
            .thenCheck { checkIfMovieItemsIsPopulated(movies) }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        val movie = movieDataPreview()
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs

        robotScreen
            .givenArrange { itemClickedArrange() }
            .whenAction { itemClickedAction() }

        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
