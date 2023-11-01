package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import com.fappslab.libraries.arch.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Robot base class.
 *
 * @param RC RobotCheck type
 * @param S State type
 * @param A Action type
 * @param VM ViewModel type
 */
@VisibleForTesting
abstract class Robot<RC : RobotCheck<RC>, S, A, VM : ViewModel<S, A>> : RobotCheck<RC> {
    abstract val robotCheck: RC
    abstract val fakeState: MutableStateFlow<S>
    abstract val fakeAction: MutableSharedFlow<A>
    abstract val fakeViewModel: VM
    abstract val subject: @Composable (viewModel: VM) -> Unit
}

@VisibleForTesting
inline fun <reified R : Robot<*, *, *, *>> R.givenArrange(noinline block: R.() -> Unit): R {
    return this.apply(block)
}

@VisibleForTesting
inline fun <reified R : Robot<RC, S, A, VM>, RC : RobotCheck<RC>, S, A, VM : ViewModel<S, A>> R.whenAction(
    noinline block: R.() -> Unit = {}
): R {
    composeTestRule.setContent { subject(fakeViewModel) }
    return this.apply(block)
}

@VisibleForTesting
inline fun <reified R : Robot<RC, *, *, *>, reified RC : RobotCheck<RC>> R.thenCheck(noinline block: RC.() -> Unit) {
    (robotCheck as? RC)?.apply(block)
}
