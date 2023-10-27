package com.fappslab.tmdbcompose.core.arch.viewmodel.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fappslab.tmdbcompose.core.arch.viewmodel.ViewModel

@NonRestartableComposable
@Composable
inline fun <reified S, reified A> OnViewState(
    viewModel: ViewModel<S, A>,
    crossinline content: @Composable (state: State<S>) -> Unit,
) {
    content(viewModel.state.collectAsStateWithLifecycle())
}
