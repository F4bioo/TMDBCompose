package com.fappslab.features.detail.presentation.robot

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.libraries.arch.viewmodel.ViewModel
import io.mockk.MockKMatcherScope
import io.mockk.every
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


@VisibleForTesting
abstract class Robot<RC : RobotCheck<RC>, S, A, VM : ViewModel<S, A>>(
    private val robotCheck: RC,
    private val composeTestRule: ComposeContentTestRule,
    private val subject: @Composable (viewModel: VM) -> Unit
) {

    protected abstract val fakeState: MutableStateFlow<S>
    protected abstract val fakeAction: MutableSharedFlow<A>
    protected abstract val fakeViewModel: VM

    open fun givenState(
        state: () -> S
    ): Robot<RC, S, A, VM> {
        fakeState.update { state() }
        return this
    }

    open fun everyState(
        onInvoke: MockKMatcherScope.(viewModel: VM) -> Unit,
        doReturn: () -> S
    ): Robot<RC, S, A, VM> {
        every { onInvoke(fakeViewModel) } answers {
            fakeState.update { doReturn() }
        }
        return this
    }

    open fun everyAction(
        onInvoke: MockKMatcherScope.(viewModel: VM) -> Unit,
        doReturn: () -> A
    ): Robot<RC, S, A, VM> {
        every { onInvoke(fakeViewModel) } coAnswers {
            fakeAction.emit(doReturn())
        }
        return this
    }

    open fun whenLaunch(
        ruleBlock: ComposeContentTestRule.() -> Unit = {}
    ): Robot<RC, S, A, VM> {
        composeTestRule.setContent {
            subject(fakeViewModel)
        }
        ruleBlock(composeTestRule)
        return this
    }

    open fun thenCheck(
        checkBlock: RC.() -> Unit
    ): RC {
        checkBlock(robotCheck)
        return robotCheck
    }
}
