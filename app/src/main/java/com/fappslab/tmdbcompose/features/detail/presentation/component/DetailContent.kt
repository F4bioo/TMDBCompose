package com.fappslab.tmdbcompose.features.detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.tmdbcompose.R
import com.fappslab.tmdbcompose.core.data.model.extension.toMovie
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.detail.presentation.viewmodel.DetailViewState
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import kotlinx.coroutines.flow.flowOf

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    state: DetailViewState,
    paddingValues: PaddingValues,
    pagingItems: LazyPagingItems<Movie>,
    onFavorite: (movie: Movie) -> Unit,
    onItemClick: (id: Int) -> Unit,
    onCollapse: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackdropView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                state = state
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onFavorite(state.detail.toMovie())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = state.favoriteIconColor
                    )
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = state.detail.title,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                mainAxisSpacing = 10.dp,
                mainAxisAlignment = MainAxisAlignment.Center,
                crossAxisSpacing = 10.dp
            ) {
                state.detail.genres.forEach { genre ->
                    GenreView(genre = genre)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            InfoGroupView(
                modifier = Modifier.fillMaxWidth(),
                detail = state.detail
            )
            Spacer(modifier = Modifier.height(8.dp))
            RatingView(
                modifier = Modifier.height(15.dp),
                rating = (state.detail.voteAverage.toFloat().div(other = 2f))
            )
            Spacer(modifier = Modifier.height(15.dp))
            SynopsisView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shouldCollapseText = state.shouldCollapseText,
                overview = state.detail.overview,
                onCollapse = onCollapse
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(horizontal = 8.dp),
                text = stringResource(id = R.string.movies_similar),
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        SimilarView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.35f)
                .align(Alignment.BottomCenter),
            paddingValues = paddingValues,
            pagingItems = pagingItems,
            onItemClick = onItemClick,
            onRetry = onRetry
        )
    }
}

@Preview
@Composable
fun DetailContentPreview() {
    val detail = Detail(
        id = 1,
        title = "Avatar: The Way of Water",
        genres = listOf("Science Fiction", "Adventure", "Action"),
        overview = "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        releaseDate = "2022-12-14",
        voteAverage = 7.65,
        duration = 192,
        voteCount = 9977
    )
    val state = DetailViewState(detail = detail)
    val pagingItems = flowOf(PagingData.from(emptyList<Movie>()))
        .collectAsLazyPagingItems()

    DetailContent(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        paddingValues = PaddingValues(),
        pagingItems = pagingItems,
        onFavorite = {},
        onItemClick = {},
        onCollapse = {},
        onRetry = {}
    )
}
