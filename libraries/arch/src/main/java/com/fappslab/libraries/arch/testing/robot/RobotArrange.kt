package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.lifecycle.ViewModel

@VisibleForTesting
abstract class RobotArrange<SELF : RobotArrange<SELF, *, *>, RA : RobotAction<*, *>, VM : ViewModel> {
    abstract val composeTestRule: ComposeContentTestRule
    abstract val subject: @Composable (VM) -> Unit
    abstract val fakeViewModel: VM
}

@VisibleForTesting
inline fun <T : RobotArrange<*, RA, VM>, VM : ViewModel, reified RA : RobotAction<*, *>> T.givenArrange(
    noinline block: T.() -> Unit = {}
): RA {
    composeTestRule.setContent {
        subject(fakeViewModel)
    }
    this.apply(block)
    return RA::class.constructors.first().call(composeTestRule)
}
