package com.fappslab.features.favorite.presentation.robot

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.favorite.presentation.component.ITEM_VIEW_TAG
import com.fappslab.libraries.arch.testing.robot.RobotCheck
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG

internal class FavoriteScreenRobotCheck(
    override val composeTestRule: ComposeContentTestRule
) : RobotCheck {

    fun checkIfHasExactlyText(text: String) {
        composeTestRule.onNodeWithText(text).assertExists()
    }

    fun checkIfFavoriteToggleIsUnchecked() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).assertIsOff()
    }

    fun checkIfMovieItemsIsPopulated(movies: List<Movie>) {
        repeat(movies.size) { index ->
            val id = movies[index].id
            composeTestRule.onNodeWithTag("${ITEM_VIEW_TAG}_$id").assertExists()
        }
    }

    fun checkIfToolbarHasExactlyText() {
        val expectedTitle = "Favorite Movies"
        composeTestRule.onNodeWithTag(APP_BAR_VIEW_TAG)
            .onChild()
            .assertTextEquals(expectedTitle)
    }
}
