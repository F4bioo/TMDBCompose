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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.libraries.design.component.preview.movieDataPreview

@Composable
fun CoverView(
    id: Int,
    title: String,
    imageUrl: String,
    voteAverage: Double,
    onItemClick: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
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

@Preview
@Composable
fun CoverViewPreview() {
    val movie = movieDataPreview()

    CoverView(
        id = movie.id,
        title = movie.title,
        imageUrl = movie.imageUrl,
        voteAverage = movie.voteAverage,
        onItemClick = {}
    )
}
