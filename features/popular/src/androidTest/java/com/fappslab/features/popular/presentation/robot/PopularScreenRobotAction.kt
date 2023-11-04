package com.fappslab.features.popular.presentation.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.fappslab.libraries.arch.testing.robot.RobotAction
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview

internal class PopularScreenRobotAction(
    override val composeTestRule: ComposeContentTestRule
) : RobotAction<PopularScreenRobotCheck> {

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick()
    }
}
