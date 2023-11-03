package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting

@VisibleForTesting
interface RobotAction<RC : RobotCheck> : RobotScreen

@VisibleForTesting
inline fun <T : RobotAction<RC>, reified RC : RobotCheck> T.whenAction(
    noinline block: T.() -> Unit = {}
): RC {
    this.apply(block)
    return RC::class.createInstance(composeTestRule)
}
