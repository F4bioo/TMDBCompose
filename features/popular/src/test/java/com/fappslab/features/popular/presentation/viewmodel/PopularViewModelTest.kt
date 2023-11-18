package com.fappslab.features.popular.presentation.viewmodel

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import app.cash.turbine.test
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.popular.domain.usecase.GetMoviesUseCase
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class PopularViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private val getMoviesUseCase: GetMoviesUseCase = mockk()
    private lateinit var subject: PopularViewModel

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMovies Should assert that movies is not null state When invoked`() = runTest {
        // Given
        val movies = PagingData.from(moviesDataPreview())
        every { getMoviesUseCase() } returns flowOf(movies)

        // When
        setupSubject()

        // Then
        subject.state.test {
            assertNotNull(awaitItem().movies)
            cancelAndConsumeRemainingEvents()
        }
        verify { getMoviesUseCase() }
    }

    @Test
    fun `onItemClicked Should emit expected action`() = runTest {
        // Given
        val expectedAction = PopularViewAction.ItemClicked(id = 1)
        every { getMoviesUseCase() } returns mockk(relaxed = true)
        setupSubject()

        // When
        subject.onItemClicked(id = 1)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify { getMoviesUseCase() }
    }

    @Test
    fun `onTryAgain Should expose expected action When invoked`() = runTest {
        // Given
        val pagingItems = mockk<LazyPagingItems<Movie>>()
        val expectedAction = PopularViewAction.TryAgain(pagingItems)
        every { getMoviesUseCase() } returns mockk(relaxed = true)
        setupSubject()

        // When
        subject.onTryAgain(pagingItems)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun setupSubject() {
        subject = PopularViewModel(
            getMoviesUseCase = getMoviesUseCase
        )
    }
}
