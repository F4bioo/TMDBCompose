package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting

@VisibleForTesting
abstract class RobotCheck<SELF : RobotCheck<SELF>> : RobotAction<SELF, RobotCheck<*>>()

@VisibleForTesting
fun <T : RobotCheck<*>> T.thenCheck(block: T.() -> Unit): T {
    return this.apply(block)
}
