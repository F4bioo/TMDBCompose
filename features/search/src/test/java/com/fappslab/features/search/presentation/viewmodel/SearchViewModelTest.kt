package com.fappslab.features.search.presentation.viewmodel

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import app.cash.turbine.test
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.search.domain.usecase.GetSearchMovieUseCase
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class SearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private val initialState = SearchViewState()
    private val getSearchMovieUseCase: GetSearchMovieUseCase = mockk()
    private lateinit var subject: SearchViewModel

    @Before
    fun setUp() {
        subject = SearchViewModel(
            getSearchMovieUseCase = getSearchMovieUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getSearchMovie Should assert that movies is not null state When invoked`() = runTest {
        // Given
        val movies = PagingData.from(moviesDataPreview())
        every { getSearchMovieUseCase(any()) } returns flowOf(movies)

        // When
        subject.getSearchMovie(query = "Avatar")

        // Then
        subject.state.test {
            assertNotNull(awaitItem().movies)
            cancelAndConsumeRemainingEvents()
        }
        verify { getSearchMovieUseCase(any()) }
    }

    @Test
    fun `onSearch Should expose states When invoked`() = runTest {
        // Given
        val expectedState = initialState.copy(query = "Avatar", shouldShowLoading = true)

        // When
        subject.onSearch(queryChanged = "Avatar")

        // Then
        subject.state.test {
            assertEquals(expectedState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onItemClicked Should emit expected action`() = runTest {
        // Given
        val expectedAction = SearchViewAction.ItemClicked(id = 1)

        // When
        subject.onItemClicked(id = 1)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onTryAgain Should expose expected action When invoked`() = runTest {
        // Given
        val pagingItems = mockk<LazyPagingItems<Movie>>()
        val expectedAction = SearchViewAction.TryAgain(pagingItems)

        // When
        subject.onTryAgain(pagingItems)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
