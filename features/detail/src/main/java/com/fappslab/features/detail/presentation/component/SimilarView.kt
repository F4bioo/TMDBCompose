package com.fappslab.features.detail.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.design.component.CoverView
import com.fappslab.libraries.design.component.ErrorView
import com.fappslab.libraries.design.component.LoadingView
import com.fappslab.tmdbcompose.features.detail.R
import kotlinx.coroutines.flow.flowOf

@Composable
fun SimilarView(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    pagingItems: LazyPagingItems<Movie>,
    onItemClick: (id: Int) -> Unit,
    onRetry: () -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 3),
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalArrangement = Arrangement.Top
    ) {
        items(pagingItems.itemCount) { index ->
            val movie = pagingItems[index]
            movie?.let {
                CoverView(
                    id = it.id,
                    title = it.title,
                    voteAverage = it.voteAverage,
                    imageUrl = it.imageUrl,
                    onItemClick = onItemClick
                )
            }
        }
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading ||
                        loadState.append is LoadState.Loading -> {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        LoadingView()
                    }
                }

                loadState.refresh is LoadState.Error ||
                        loadState.append is LoadState.Error -> {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {
                        ErrorView(
                            message = stringResource(id = R.string.common_error_message)
                        ) {
                            onRetry()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SimilarViewPreview() {
    val pagingItems = flowOf(PagingData.from(emptyList<Movie>()))
        .collectAsLazyPagingItems()

    SimilarView(
        modifier = Modifier.fillMaxWidth(),
        paddingValues = PaddingValues(),
        pagingItems = pagingItems,
        onItemClick = {},
        onRetry = {}
    )
}
