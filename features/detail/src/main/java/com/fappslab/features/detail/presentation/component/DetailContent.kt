package com.fappslab.features.detail.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.presentation.viewmodel.DetailViewState
import com.fappslab.libraries.design.component.CoverView
import com.fappslab.libraries.design.component.FooterItemView
import com.fappslab.libraries.design.component.preview.detailDataPreview
import kotlinx.coroutines.flow.flowOf

internal const val LIST_VIEW_TAG = "ListViewTag"

@Composable
internal fun DetailContent(
    modifier: Modifier = Modifier,
    state: DetailViewState,
    paddingValues: PaddingValues,
    pagingItems: LazyPagingItems<Movie>,
    onFavorite: (movie: Movie) -> Unit,
    onItemClick: (id: Int) -> Unit,
    onCollapse: (isExpanded: Boolean) -> Unit,
    onRetry: () -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag(LIST_VIEW_TAG),
        columns = GridCells.Fixed(count = 3),
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalArrangement = Arrangement.Top
    ) {
        item(
            span = {
                GridItemSpan(maxLineSpan)
            }
        ) {
            HeaderItemView(
                state = state,
                onFavorite = onFavorite,
                onCollapse = onCollapse
            )
        }

        items(
            count = pagingItems.itemCount
        ) { index ->
            CoverView(
                movie = pagingItems[index],
                onItemClick = onItemClick
            )
        }

        item(
            span = {
                GridItemSpan(maxLineSpan)
            }
        ) {
            FooterItemView(
                loadState = pagingItems.loadState,
                onRetry = onRetry
            )
        }
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
