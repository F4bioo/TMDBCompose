package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

@VisibleForTesting
interface RobotArrange<RA : RobotAction<*>, VM : ViewModel> : RobotScreen {
    val subject: @Composable (VM) -> Unit
    val fakeViewModel: VM
}

@VisibleForTesting
inline fun <T : RobotArrange<RA, VM>, VM : ViewModel, reified RA : RobotAction<*>> T.givenArrange(
    noinline block: T.() -> Unit = {}
): RA {
    composeTestRule.setContent {
        subject(fakeViewModel)
    }
    this.apply(block)
    return RA::class.createInstance(composeTestRule)
}
