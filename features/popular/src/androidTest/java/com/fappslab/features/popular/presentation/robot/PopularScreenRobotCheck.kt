package com.fappslab.features.popular.presentation.robot

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.arch.testing.robot.RobotCheck
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.COVER_VIEW_TAG

internal class PopularScreenRobotCheck(
    override val composeTestRule: ComposeContentTestRule
) : RobotCheck<PopularScreenRobotCheck> {

    fun checkIfToolbarHasExactlyText() {
        val expectedTitle = "Popular Movies"
        composeTestRule.onNodeWithTag(APP_BAR_VIEW_TAG)
            .onChild()
            .assertTextEquals(expectedTitle)
    }

    fun checkIfMovieItemsIsPopulated(movies: List<Movie>) {
        repeat(movies.size) { index ->
            val id = movies[index].id
            composeTestRule.onNodeWithTag("${COVER_VIEW_TAG}_$id").assertExists()
        }
    }
}
