package com.fappslab.features.favorite.domain

import app.cash.turbine.test
import com.fappslab.core.domain.repository.FavoriteRepository
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class GetFavoritesUseCaseTest {

    private val repository: FavoriteRepository = mockk()
    private val subject = GetFavoritesUseCase(repository)

    @Test
    fun `getFavorites Should return favorite movies When invoked`() = runTest {
        // Given
        val expectedResult = moviesDataPreview()
        every { repository.getFavorites() } returns flowOf(expectedResult)

        // When
        val result = subject()

        // Then
        result.test {
            assertEquals(expectedResult, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        verify { repository.getFavorites() }
    }
}
