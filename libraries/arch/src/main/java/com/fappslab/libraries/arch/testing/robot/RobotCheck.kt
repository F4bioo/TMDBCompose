package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.test.junit4.ComposeContentTestRule

@VisibleForTesting
interface RobotCheck<RC : RobotCheck<RC>> {

    val composeTestRule: ComposeContentTestRule

    @Suppress("UNCHECKED_CAST")
    fun thenCheck(checkBlock: RC.() -> Unit): RC {
        checkBlock(this as RC)
        return this
    }
}
