package com.fappslab.features.detail.presentation.component

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
import androidx.compose.material.Text
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
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.data.model.extension.toMovie
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.design.component.FavoriteToggleView
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.features.detail.R
import com.fappslab.tmdbcompose.features.detail.presentation.component.RatingView
import com.fappslab.tmdbcompose.features.detail.presentation.component.SynopsisView
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun DetailContent(
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
                FavoriteToggleView(
                    isChecked = state.isFavoriteChecked,
                    onCheckedChange = {
                        onFavorite(state.detail.toMovie())
                    }
                )
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
    val state = DetailViewState(detail = detailDataPreview())
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