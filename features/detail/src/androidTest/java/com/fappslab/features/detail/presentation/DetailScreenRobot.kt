package com.fappslab.features.detail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.features.detail.presentation.robot.Robot
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal class DetailScreenRobot(
    initialState: DetailViewState,
    robotCheck: DetailScreenRobotCheck,
    composeTestRule: ComposeContentTestRule,
    subject: @Composable (viewModel: DetailViewModel) -> Unit,
) : Robot<DetailScreenRobotCheck, DetailViewState, DetailViewAction, DetailViewModel>(
    robotCheck, composeTestRule, subject
) {

    override val fakeState = MutableStateFlow(initialState)

    override val fakeAction = MutableSharedFlow<DetailViewAction>()

    override val fakeViewModel = mockk<DetailViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }
}
