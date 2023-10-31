package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.test.junit4.ComposeContentTestRule

@VisibleForTesting
interface RobotCheck<RC> {
    val composeTestRule: ComposeContentTestRule
}
