package com.fappslab.features.detail.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.libraries.arch.viewmodel.ViewModel
import io.mockk.MockKMatcherScope
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

// TODO move to common module

@VisibleForTesting
abstract class RobotBase<S, A, VM : ViewModel<S, A>>(
    initialState: S,
    viewModelClass: KClass<VM>,
    private val composeTestRule: ComposeContentTestRule,
    private val subject: @Composable (viewModel: VM) -> Unit
) {

    private val fakeState = MutableStateFlow(initialState)
    private val fakeAction = MutableSharedFlow<A>()
    private val fakeViewModel = mockkClass(viewModelClass, relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    open fun givenState(
        state: () -> S
    ): RobotBase<S, A, VM> {
        fakeState.update { state() }
        return this
    }

    open fun everyState(
        onInvoke: MockKMatcherScope.(viewModel: VM) -> Unit,
        doReturn: () -> S
    ): RobotBase<S, A, VM> {
        every { onInvoke(fakeViewModel) } answers {
            fakeState.update { doReturn() }
        }
        return this
    }

    open fun everyAction(
        onInvoke: MockKMatcherScope.(viewModel: VM) -> Unit,
        doReturn: () -> A
    ): RobotBase<S, A, VM> {
        every { onInvoke(fakeViewModel) } coAnswers {
            fakeAction.emit(doReturn())
        }
        return this
    }

    open fun whenLaunch(
        ruleBlock: ComposeContentTestRule.() -> Unit = {}
    ): RobotBase<S, A, VM> {
        composeTestRule.setContent {
            subject(fakeViewModel)
        }
        ruleBlock(composeTestRule)
        return this
    }

    open fun thenCheck(
        ruleBlock: ComposeContentTestRule.() -> Unit
    ): RobotBase<S, A, VM> {
        ruleBlock(composeTestRule)
        return this
    }
}
