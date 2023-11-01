package com.fappslab.libraries.arch.testing.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import com.fappslab.libraries.arch.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Robot base class.
 *
 * @param RA RobotAction type
 * @param RC RobotCheck type
 * @param S State type
 * @param A Action type
 * @param VM ViewModel type
 */
@VisibleForTesting
abstract class RobotArrange<RA : RobotAction<RA>, RC : RobotCheck<RC>, S, A, VM : ViewModel<S, A>> {
    abstract val robotAction: RA
    abstract val robotCheck: RC
    abstract val fakeState: MutableStateFlow<S>
    abstract val fakeAction: MutableSharedFlow<A>
    abstract val fakeViewModel: VM
    abstract val subject: @Composable (viewModel: VM) -> Unit
}

@VisibleForTesting
inline fun <reified R : RobotArrange<*, *, *, *, *>> R.givenArrange(
    noinline block: R.() -> Unit
): R {
    return this.apply(block)
}
