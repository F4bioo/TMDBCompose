package com.fappslab.features.detail.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.presentation.component.MOVIE_TITLE_VIEW_TAG
import com.fappslab.features.detail.presentation.component.PROGRESS_HEADER_VIEW_TAG
import com.fappslab.libraries.arch.testing.robot.RobotCheck
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG

internal class DetailScreenRobotCheck(
    override val composeTestRule: ComposeContentTestRule
) : RobotCheck<DetailScreenRobotCheck> {

    fun checkIfHasExactlyText(text: String) {
        composeTestRule.onNodeWithText(text).assertExists()
    }

    fun checkIfToolbarHasExactlyText() {
        val expectedTitle = "Movie Detail"
        composeTestRule.onNodeWithTag(APP_BAR_VIEW_TAG)
            .onChild()
            .assertTextEquals(expectedTitle)
    }

    fun checkIfLoadingIsDisplayed() {
        composeTestRule.onNodeWithTag(PROGRESS_HEADER_VIEW_TAG).assertIsDisplayed()
    }

    fun checkIfFavoriteToggleIsChecked() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).assertIsOn()
    }

    fun checkIfMovieTitleHasExactlyText() {
        val expectedTitle = "Avatar: The Way of Water"
        composeTestRule.onNodeWithTag(MOVIE_TITLE_VIEW_TAG).assertTextEquals(expectedTitle)
    }

    fun checkIfHasExactlyGenreList() {
        val expectedGenres = listOf("Science Fiction", "Adventure", "Action")
        expectedGenres.forEach { genre ->
            composeTestRule.onNodeWithText(genre).assertExists()
        }
    }

    fun checkIfArrowIconIsChanged() {
        composeTestRule.onNodeWithTag(Icons.Default.KeyboardArrowUp.name, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun checkIfMovieItemsIsPopulated(movies: List<Movie>) {
        repeat(movies.size) { index ->
            val id = movies[index].id
            composeTestRule.onNodeWithTag("${COVER_VIEW_TAG}_$id").assertExists()
        }
    }
}
