package com.fappslab.features.detail.presentation.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.fappslab.features.detail.presentation.component.SYNOPSIS_VIEW_TAG
import com.fappslab.libraries.arch.testing.robot.RobotAction
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview

internal class DetailScreenRobotAction(
    override val composeTestRule: ComposeContentTestRule
) : RobotAction<DetailScreenRobotAction, DetailScreenRobotCheck>() {

    fun favoriteCheckedAction() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick()
    }

    fun toggleExpandedAction() {
        composeTestRule.onNodeWithTag(SYNOPSIS_VIEW_TAG).performClick()
    }

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${COVER_VIEW_TAG}_${movie.id}").performClick()
    }
}
