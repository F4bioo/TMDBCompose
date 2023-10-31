package com.fappslab.libraries.design.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.fappslab.tmdbcompose.libraries.design.R

@Composable
fun FooterItemView(
    shouldShowLoading: Boolean = true,
    loadState: CombinedLoadStates,
    onRetry: () -> Unit
) {
    when {
        shouldShowLoading && loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
            LoadingView()
        }

        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
            ErrorView(
                message = stringResource(id = R.string.common_error_message)
            ) {
                onRetry()
            }
        }
    }
}
