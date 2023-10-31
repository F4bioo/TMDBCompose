package com.fappslab.features.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.search.presentation.viewmodel.SearchViewAction
import com.fappslab.features.search.presentation.viewmodel.SearchViewModel
import com.fappslab.features.search.presentation.viewmodel.SearchViewState
import com.fappslab.libraries.design.component.APP_BAR_VIEW_TAG
import com.fappslab.libraries.design.component.COVER_VIEW_TAG
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SearchScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialState = SearchViewState()
    private val navController = mockk<NavHostController>()
    private val detailNavigation = mockk<DetailNavigation>()

    private val fakeState = MutableStateFlow(initialState)
    private val fakeAction = MutableSharedFlow<SearchViewAction>()
    private val fakeViewModel = mockk<SearchViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    @Composable
    private fun Subject() {
        SearchScreen(
            navController = navController,
            detailNavigation = detailNavigation,
            viewModel = fakeViewModel
        )
    }

    @Test
    fun toolbarTitle_Should_displayTopBarTile_When_screenIsShowing() {
        // Given
        val expectedTitle = "Movie Search"

        // When
        composeRule.setContent { Subject() }

        // Then
        composeRule.onNodeWithTag(APP_BAR_VIEW_TAG)
            .onChild()
            .assertTextEquals(expectedTitle)
    }

    @Test
    fun detailContent_Should_PopulateMovieList_When_ThereAreMovies() {
        // Given
        val movies = moviesDataPreview()
        fakeState.update { it.copy(movies = flowOf(PagingData.from(movies))) }

        // When
        composeRule.setContent { Subject() }

        // Then
        repeat(movies.size) { index ->
            val id = movies[index].id
            composeRule.onNodeWithTag("${COVER_VIEW_TAG}_$id")
                .assertExists()
        }
    }

    @Test
    fun itemClicked_Should_Navigate_When_ClickedOnMovieItem() {
        // Given
        val movies = moviesDataPreview()
        val movie = movies.first()
        fakeState.update { it.copy(movies = flowOf(PagingData.from(movies))) }
        every { detailNavigation.navigateToDetail(navController, movie.id) } just Runs
        every { fakeViewModel.onItemClicked(any()) } coAnswers {
            fakeAction.emit(SearchViewAction.ItemClicked(movie.id))
        }

        // When
        composeRule.setContent { Subject() }

        // Then
        composeRule.onNodeWithTag("${COVER_VIEW_TAG}_${movie.id}")
            .performClick()
        verify { detailNavigation.navigateToDetail(navController, movie.id) }
    }
}
