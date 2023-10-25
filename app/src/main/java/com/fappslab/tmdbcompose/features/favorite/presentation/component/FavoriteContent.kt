package com.fappslab.tmdbcompose.features.favorite.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.core.presentaion.component.preview.moviesDataPreview

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    movies: List<Movie>,
    isFavorite: Boolean,
    onCheckedChange: (movie: Movie) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Box(
        modifier = modifier.background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues,
            content = {
                items(
                    items = movies,
                    key = { movie ->
                        movie.id
                    }
                ) { movie ->
                    ItemView(
                        movie = movie,
                        isFavorite = isFavorite,
                        onCheckedChange = onCheckedChange,
                        onItemClick = onItemClick
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun FavoriteContentPreview() {
    FavoriteContent(
        paddingValues = PaddingValues(),
        movies = moviesDataPreview(),
        isFavorite = true,
        onCheckedChange = {},
        onItemClick = {}
    )
}
