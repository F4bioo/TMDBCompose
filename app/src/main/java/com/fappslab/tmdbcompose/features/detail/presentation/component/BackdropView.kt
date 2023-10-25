package com.fappslab.tmdbcompose.features.detail.presentation.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fappslab.tmdbcompose.core.presentaion.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.tmdbcompose.ui.theme.yellow

@Composable
fun BackdropView(
    modifier: Modifier = Modifier,
    state: DetailViewState
) {
    Box(modifier = modifier.background(Color(0xFF2F2F2F))) {
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
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.detail.imageUrl)
                .crossfade(enable = true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        if (state.shouldShowLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = yellow
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
