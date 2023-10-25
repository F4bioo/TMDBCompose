package com.fappslab.tmdbcompose.features.favorite.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.core.presentaion.component.FavoriteToggleView
import com.fappslab.tmdbcompose.core.presentaion.component.RateView

@Composable
fun ItemView(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFavorite: Boolean,
    onCheckedChange: (movie: Movie) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                onItemClick(movie.id)
            },
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color(0xFF282828))
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp),
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.DarkGray)
                    )
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.imageUrl)
                            .crossfade(enable = true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    RateView(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(Color(0x80000000)),
                        voteAverage = movie.voteAverage
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color(0xFF424242))
                ) {
                    FavoriteToggleView(
                        modifier = Modifier.align(Alignment.TopEnd),
                        isChecked = isFavorite,
                        onCheckedChange = {
                            onCheckedChange(movie)
                        }
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .padding(8.dp),
                        text = movie.title,
                        maxLines = 2,
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemViewPreview() {
    val movie = Movie(
        id = 1,
        title = "Avatar: The Way of Water",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        voteAverage = 7.65,
    )
    ItemView(
        movie = movie,
        isFavorite = true,
        onCheckedChange = {},
        onItemClick = {}
    )
}