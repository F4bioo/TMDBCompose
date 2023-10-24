package com.fappslab.tmdbcompose.core.presentaion.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
            RateView(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .zIndex(2f)
                    .background(Color(0x80000000))
                    .fillMaxWidth(),
                voteAverage = voteAverage
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

@Preview
@Composable
fun CoverViewPreview() {
    CoverView(
        id = 1,
        title = "Avatar: The Way of Water",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        voteAverage = 5.0,
        onItemClick = {}
    )
}