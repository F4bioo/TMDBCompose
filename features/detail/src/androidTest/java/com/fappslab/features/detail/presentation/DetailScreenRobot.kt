package com.fappslab.features.detail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.fappslab.features.detail.presentation.viewmodel.DetailViewAction
import com.fappslab.features.detail.presentation.viewmodel.DetailViewModel
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import kotlin.reflect.KClass

internal class DetailScreenRobot(
    initialState: DetailViewState,
    viewModelClass: KClass<DetailViewModel>,
    composeTestRule: ComposeContentTestRule,
    subject: @Composable (viewModel: DetailViewModel) -> Unit
) : RobotBase<DetailViewState, DetailViewAction, DetailViewModel>(
    initialState, viewModelClass, composeTestRule, subject
)
