package com.fappslab.features.favorite.presentation.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.fappslab.features.favorite.presentation.component.ITEM_VIEW_TAG
import com.fappslab.libraries.arch.testing.robot.RobotAction
import com.fappslab.libraries.design.component.FAVORITE_TOGGLE_VIEW_TAG
import com.fappslab.libraries.design.component.preview.movieDataPreview

internal class FavoriteScreenRobotAction(
    override val composeTestRule: ComposeContentTestRule
) : RobotAction<FavoriteScreenRobotAction> {

    fun favoriteUncheckedAction() {
        composeTestRule.onNodeWithTag(FAVORITE_TOGGLE_VIEW_TAG).performClick()
    }

    fun itemClickedAction() {
        val movie = movieDataPreview()
        composeTestRule.onNodeWithTag(testTag = "${ITEM_VIEW_TAG}_${movie.id}").performClick()
    }
}
