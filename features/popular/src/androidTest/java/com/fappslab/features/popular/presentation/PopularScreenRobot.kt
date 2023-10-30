package com.fappslab.features.popular.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.features.popular.presentation.viewmodel.PopularViewAction
import com.fappslab.features.popular.presentation.viewmodel.PopularViewModel
import com.fappslab.features.popular.presentation.viewmodel.PopularViewState
import com.fappslab.libraries.arch.testing.robot.Robot
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal class PopularScreenRobot(
    initialState: PopularViewState,
    composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: PopularViewModel) -> Unit
) : Robot<PopularScreenRobotCheck, PopularViewState, PopularViewAction, PopularViewModel>(
    composeTestRule
) {

    override val robotCheck = PopularScreenRobotCheck(composeTestRule)
    override val fakeState = MutableStateFlow(initialState)
    override val fakeAction = MutableSharedFlow<PopularViewAction>()
    override val fakeViewModel = mockk<PopularViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }
}
