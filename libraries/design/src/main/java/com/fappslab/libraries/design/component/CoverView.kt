package com.fappslab.libraries.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.design.component.preview.movieDataPreview

const val COVER_VIEW_TAG = "CoverView"

@Composable
fun CoverView(
    movie: Movie?,
    onItemClick: (id: Int) -> Unit
) {
    movie?.apply {
        Card(
            modifier = Modifier
                .testTag("${COVER_VIEW_TAG}_$id")
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
                .clickable {
                    onItemClick(id)
                },
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier.background(Color(0xFF2F2F2F))
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(8.dp),
                    text = title,
                    maxLines = 2,
                    fontSize = 14.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                ImageLoaderView(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    imageUrl = imageUrl
                )
                RateView(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color(0x80000000)),
                    voteAverage = voteAverage
                )
            }
        }
    }
}

@Preview
@Composable
fun CoverViewPreview() {
    val movie = movieDataPreview()

    CoverView(
        movie = movie,
        onItemClick = {}
    )
}
