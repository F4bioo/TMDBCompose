package com.fappslab.features.detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.design.component.ImageLoaderView
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.theme.red

internal const val BACKDROP_VIEW_TAG = "BackdropView"
internal const val PROGRESS_HEADER_VIEW_TAG = "ProgressHeaderView"

@Composable
internal fun BackdropView(
    modifier: Modifier = Modifier,
    state: DetailViewState
) {
    Box(
        modifier = modifier
            .background(Color(0xFF2F2F2F))
            .testTag(BACKDROP_VIEW_TAG)
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(8.dp),
            text = state.detail.title,
            maxLines = 2,
            fontSize = 14.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        ImageLoaderView(
            modifier = Modifier.fillMaxSize(),
            imageUrl = state.detail.imageUrl
        )
        if (state.shouldShowLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(PROGRESS_HEADER_VIEW_TAG),
                color = red
            )
        }
        if (state.errorMessage.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = state.errorMessage,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun BackdropViewPreview() {
    val state = DetailViewState(detail = detailDataPreview())

    BackdropView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        state = state
    )
}
