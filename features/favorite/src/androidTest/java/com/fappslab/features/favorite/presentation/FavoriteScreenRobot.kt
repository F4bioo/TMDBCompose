package com.fappslab.features.favorite.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewAction
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.fappslab.features.favorite.presentation.viewmodel.FavoriteViewState
import com.fappslab.libraries.arch.testing.robot.Robot
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal class FavoriteScreenRobot(
    initialState: FavoriteViewState,
    composeTestRule: ComposeContentTestRule,
    override val subject: @Composable (viewModel: FavoriteViewModel) -> Unit
) : Robot<FavoriteScreenRobotCheck, FavoriteViewState, FavoriteViewAction, FavoriteViewModel>(
    composeTestRule
) {

    override val robotCheck = FavoriteScreenRobotCheck(composeTestRule)
    override val fakeState = MutableStateFlow(initialState)
    override val fakeAction = MutableSharedFlow<FavoriteViewAction>()
    override val fakeViewModel = mockk<FavoriteViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }
}
