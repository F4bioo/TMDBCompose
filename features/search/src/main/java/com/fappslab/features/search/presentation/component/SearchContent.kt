package com.fappslab.features.search.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.fappslab.features.search.presentation.viewmodel.SearchViewState
import com.fappslab.libraries.design.component.CoverView
import com.fappslab.libraries.design.component.FooterItemView

@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    state: SearchViewState,
    paddingValues: PaddingValues,
    pagingItems: LazyPagingItems<Movie>,
    onSearch: (query: String) -> Unit,
    onValueChange: (queryChanged: String) -> Unit,
    onItemClick: (id: Int) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SearchView(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            query = state.query,
            onSearch = { query ->
                onSearch(query)
            },
            onValueChange = { action ->
                onValueChange(action)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

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
                    shouldShowLoading = state.shouldShowLoading,
                    loadState = pagingItems.loadState,
                    onRetry = onRetry
                )
            }
        }
    }
}
