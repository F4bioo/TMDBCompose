package com.fappslab.features.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import app.cash.turbine.test
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.domain.model.Pack
import com.fappslab.features.detail.domain.usecase.provider.DetailUseCaseProvider
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private val keyArgs = "DETAILS_ID_ARGS_KEY"
    private val initialState = DetailViewState()
    private val provider = DetailUseCaseProvider(
        getMovieDetailUseCase = mockk(),
        isFavoriteUseCase = mockk(),
        setFavoriteUseCase = mockk(),
        deleteFavoriteUseCase = mockk(),
    )
    private val savedStateHandle: SavedStateHandle = mockk()
    private lateinit var subject: DetailViewModel

    @Before
    fun setUp() {
        subject = DetailViewModel(
            provider = provider,
            savedStateHandle = savedStateHandle
        )
        every { savedStateHandle.get<Int>(keyArgs) } returns 1
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMovieDetailSuccess Should expose expected states When invoked`() = runTest {
        // Given
        val detail = detailDataPreview()
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState
            .copy(detail = detail, movies = movies, isFavoriteChecked = true)
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)
        val pack = Pack(detail, movies)
        coEvery { provider.getMovieDetailUseCase(any()) } returns pack
        coEvery { provider.isFavoriteUseCase(any()) } returns true

        // When
        subject.getMovieDetail()

        // Then
        subject.state.test {
            assertEquals(initialState, awaitItem())
            assertEquals(expectedFirstState, awaitItem())

            val secondState = awaitItem()
            assertEquals(expectedSecondState.detail, secondState.detail)
            assertEquals(expectedSecondState.isFavoriteChecked, secondState.isFavoriteChecked)
            assertNotNull(secondState.movies)

            assertEquals(expectedFinalState.shouldShowLoading, awaitItem().shouldShowLoading)
            cancelAndConsumeRemainingEvents()
        }
        coVerify { provider.getMovieDetailUseCase(any()) }
        coVerify { provider.isFavoriteUseCase(any()) }
        verify { savedStateHandle.get<Int>(keyArgs) }
    }

    @Test
    fun `getMovieDetailFailure Should expose expected states When invoked`() = runTest {
        // Given
        val message = "Some error message"
        val cause = Throwable(message)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(errorMessage = message)
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)
        coEvery { provider.getMovieDetailUseCase(any()) } throws cause
        coEvery { provider.isFavoriteUseCase(any()) } returns true

        // When
        subject.getMovieDetail()

        // Then
        subject.state.test {
            assertEquals(initialState, awaitItem())
            assertEquals(expectedFirstState, awaitItem())
            assertEquals(expectedSecondState, awaitItem())

            val finalState = awaitItem()
            assertEquals(expectedFinalState.errorMessage, finalState.errorMessage)
            cancelAndConsumeRemainingEvents()
        }
        coVerify { provider.getMovieDetailUseCase(any()) }
        coVerify(exactly = 0) { provider.isFavoriteUseCase(any()) }
        verify { savedStateHandle.get<Int>(keyArgs) }
    }

    @Test
    fun `onItemClicked Should expose expected action When invoked`() = runTest {
        // Given
        val expectedAction = DetailViewAction.ItemClicked(id = 1)

        // When
        subject.onItemClicked(id = 1)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onCollapse Should expose expected state When invoked with true value`() = runTest {
        // Given
        val expectedState = initialState.copy(shouldCollapseText = true)

        // When
        subject.onCollapse(shouldCollapse = true)

        // Then
        subject.state.test {
            assertEquals(expectedState, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onFavorite Should remove movie from favorites When it is already a favorite`() = runTest {
        // Given
        coEvery { provider.getMovieDetailUseCase(any()) } returns mockk(relaxed = true)
        coEvery { provider.isFavoriteUseCase(any()) } returns true
        coEvery { provider.deleteFavoriteUseCase(any()) } just Runs
        val detailJob = launch { subject.getMovieDetail() }
        detailJob.join()

        // When
        val favoriteJob = launch { subject.onFavorite(movie = mockk()) }

        // Then
        subject.state.test {
            assertEquals(expected = true, awaitItem().shouldShowLoading)
            assertEquals(expected = true, awaitItem().isFavoriteChecked)
            assertEquals(expected = false, awaitItem().shouldShowLoading)
            assertEquals(expected = true, awaitItem().shouldShowLoading)
            assertEquals(expected = false, awaitItem().isFavoriteChecked)
            assertEquals(expected = false, awaitItem().shouldShowLoading)
            cancelAndConsumeRemainingEvents()
        }
        favoriteJob.join()
        coVerify { provider.getMovieDetailUseCase(any()) }
        coVerify { provider.isFavoriteUseCase(any()) }
        coVerify { provider.deleteFavoriteUseCase(any()) }
        verify { savedStateHandle.get<Int>(keyArgs) }
    }

    @Test
    fun `onFavorite Should add movie to favorites When it is not a favorite`() = runTest {
        // Given
        val movie = mockk<Movie>()
        coEvery { provider.getMovieDetailUseCase(any()) } returns mockk(relaxed = true)
        coEvery { provider.isFavoriteUseCase(any()) } returns false
        coEvery { provider.setFavoriteUseCase(any()) } just Runs
        val detailJob = launch { subject.getMovieDetail() }
        detailJob.join()

        // When
        val favoriteJob = launch { subject.onFavorite(movie) }

        // Then
        subject.state.test {
            assertEquals(expected = true, awaitItem().shouldShowLoading)
            assertEquals(expected = false, awaitItem().isFavoriteChecked)
            assertEquals(expected = false, awaitItem().shouldShowLoading)
            assertEquals(expected = true, awaitItem().shouldShowLoading)
            assertEquals(expected = true, awaitItem().isFavoriteChecked)
            assertEquals(expected = false, awaitItem().shouldShowLoading)
            cancelAndConsumeRemainingEvents()
        }
        favoriteJob.join()
        coVerify { provider.getMovieDetailUseCase(any()) }
        coVerify { provider.isFavoriteUseCase(any()) }
        coVerify { provider.setFavoriteUseCase(movie) }
        verify { savedStateHandle.get<Int>(keyArgs) }
    }


    @Test
    fun `onTryAgain Should expose expected action When invoked`() = runTest {
        // Given
        val pagingItems = mockk<LazyPagingItems<Movie>>()
        val expectedAction = DetailViewAction.TryAgain(pagingItems)

        // When
        subject.onTryAgain(pagingItems)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
