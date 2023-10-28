package com.fappslab.libraries.arch.viewmodel.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import com.fappslab.libraries.arch.viewmodel.ViewModel

@NonRestartableComposable
@Composable
inline fun <reified S, reified A> OnViewAction(
    viewModel: ViewModel<S, A>,
    crossinline action: (A) -> Unit
) {
    LaunchedEffect(viewModel) {
        viewModel.action.collect { action(it) }
    }
}
