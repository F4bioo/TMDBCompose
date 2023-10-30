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
import com.fappslab.features.detail.presentation.robot.RobotCheck
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG

internal class DetailScreenRobotCheck : RobotCheck<DetailScreenRobotCheck> {

    fun ComposeContentTestRule.checkIfHasExactlyText(text: String) {
        onNodeWithText(text).assertExists()
    }

    fun ComposeContentTestRule.checkIfToolbarHasExactlyText() {
        val expectedTitle = "Movie Detail"
        onNodeWithTag(APP_BAR_VIEW_TAG)
            .onChild()
            .assertTextEquals(expectedTitle)
    }

    fun ComposeContentTestRule.checkIfLoadingIsDisplayed() {
        onNodeWithTag(PROGRESS_HEADER_VIEW_TAG).assertIsDisplayed()
    }

    fun ComposeContentTestRule.checkIfFavoriteToggleIsChecked() {
        onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).assertIsOn()
    }

    fun ComposeContentTestRule.checkIfMovieTitleHasExactlyText() {
        val expectedTitle = "Avatar: The Way of Water"
        onNodeWithTag(MOVIE_TITLE_VIEW_TAG).assertTextEquals(expectedTitle)
    }

    fun ComposeContentTestRule.checkIfHasExactlyGenreList() {
        val expectedGenres = listOf("Science Fiction", "Adventure", "Action")
        expectedGenres.forEach { genre ->
            onNodeWithText(genre).assertExists()
        }
    }

    fun ComposeContentTestRule.checkIfArrowIconIsChanged() {
        onNodeWithTag(Icons.Default.KeyboardArrowUp.name, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun ComposeContentTestRule.checkIfMovieItemsIsPopulated(movies: List<Movie>) {
        repeat(movies.size) { index ->
            val id = movies[index].id
            onNodeWithTag("${COVER_VIEW_TAG}_$id").assertExists()
        }
    }
}
