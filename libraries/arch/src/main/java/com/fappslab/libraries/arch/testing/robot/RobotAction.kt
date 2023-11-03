package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.test.junit4.ComposeContentTestRule

@VisibleForTesting
abstract class RobotAction<SELF : RobotAction<SELF, *>, RC : RobotCheck<*>> {
    abstract val composeTestRule: ComposeContentTestRule
}

@VisibleForTesting
inline fun <T : RobotAction<*, RC>, reified RC : RobotCheck<*>> T.whenAction(
    noinline block: T.() -> Unit = {}
): RC {
    this.apply(block)
    return RC::class.constructors.first().call(composeTestRule)
}
