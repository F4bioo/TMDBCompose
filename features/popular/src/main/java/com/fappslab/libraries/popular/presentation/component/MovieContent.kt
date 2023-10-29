package com.fappslab.libraries.popular.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.design.component.CoverView
import com.fappslab.libraries.design.component.FooterItemView

@Composable
internal fun MovieContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    pagingItems: LazyPagingItems<Movie>,
    onItemClick: (id: Int) -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.background(Color.Black)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(count = 3),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalArrangement = Arrangement.Top
        ) {
            items(pagingItems.itemCount) { index ->
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
}
