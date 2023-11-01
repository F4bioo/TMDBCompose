package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.libraries.arch.viewmodel.ViewModel

@VisibleForTesting
interface RobotAction<RA> {
    val composeTestRule: ComposeContentTestRule
}

@VisibleForTesting
inline fun <reified R : RobotArrange<RA, *, S, A, VM>, reified RA : RobotAction<RA>, S, A, reified VM : ViewModel<S, A>> R.whenAction(
    noinline block: RA.() -> Unit = {}
): R {
    robotAction.apply {
        composeTestRule.setContent {
            subject(fakeViewModel)
        }
        block(this)
    }
    return this
}
