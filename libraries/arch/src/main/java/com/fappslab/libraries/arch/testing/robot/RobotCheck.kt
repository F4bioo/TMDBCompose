package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting

@VisibleForTesting
interface RobotCheck : RobotScreen

@VisibleForTesting
fun <T : RobotCheck> T.thenCheck(block: T.() -> Unit): T {
    return this.apply(block)
}
