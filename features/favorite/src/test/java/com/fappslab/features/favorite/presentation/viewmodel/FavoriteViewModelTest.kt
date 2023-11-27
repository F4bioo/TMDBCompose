package com.fappslab.features.favorite.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.core.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.features.favorite.domain.GetFavoritesUseCase
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.design.component.preview.movieDataPreview
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
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

internal class FavoriteViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private val initialState = FavoriteViewState()
    private val getFavoritesUseCase: GetFavoritesUseCase = mockk()
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = mockk()
    private lateinit var subject: FavoriteViewModel

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getFavorites Should expose states When invoked`() = runTest {
        // Given
        val movies = moviesDataPreview()
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(movies = movies)
        val expectedFinalState = expectedSecondState.copy(shouldShowLoading = false)
        every { getFavoritesUseCase() } returns flowOf(movies)

        // When
        setupSubject()

        // Then
        subject.state.test {
            assertEquals(initialState, awaitItem())
            assertEquals(expectedFirstState, awaitItem())
            assertEquals(expectedSecondState, awaitItem())
            assertEquals(expectedFinalState, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify { getFavoritesUseCase() }
    }

    @Test
    fun `onFavorite Should expose states When invoked to delete a favorite`() = runTest {
        // Given
        val movie = movieDataPreview(id = 1)
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedFinalState = expectedFirstState.copy(shouldShowLoading = false)
        every { getFavoritesUseCase() } returns mockk(relaxed = true)
        coEvery { deleteFavoriteUseCase(any()) } just Runs
        setupSubject()

        // When
        subject.onFavorite(movie)

        // Then
        subject.state.test {
            assertEquals(initialState, awaitItem())
            assertEquals(expectedFirstState, awaitItem())
            assertEquals(expectedFinalState, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify { getFavoritesUseCase() }
        coVerify { deleteFavoriteUseCase(any()) }
    }

    @Test
    fun `onItemClicked Should expose expected action When invoked`() = runTest {
        // Given
        val expectedAction = FavoriteViewAction.ItemClicked(id = 1)
        every { getFavoritesUseCase() } returns mockk(relaxed = true)
        setupSubject()

        // When
        subject.onItemClicked(id = 1)

        // Then
        subject.action.test {
            assertEquals(expectedAction, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify { getFavoritesUseCase() }
    }

    private fun setupSubject() {
        subject = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            deleteFavoriteUseCase = deleteFavoriteUseCase,
        )
    }
}
