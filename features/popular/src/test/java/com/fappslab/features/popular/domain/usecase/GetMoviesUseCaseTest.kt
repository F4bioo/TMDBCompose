package com.fappslab.features.popular.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.fappslab.features.popular.domain.repository.PopularRepository
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class GetMoviesUseCaseTest {

    private val repository: PopularRepository = mockk()
    private val subject = GetMoviesUseCase(repository)

    @Test
    fun `getMovies Should return movie flow object When invoked`() = runTest {
        // Given
        val expectedResult = PagingData.from(moviesDataPreview())
        coEvery { repository.getMovies() } returns flowOf(expectedResult)

        // When
        val result = subject()

        // Then
        result.test {
            assertEquals(expectedResult, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        coVerify { repository.getMovies() }
    }
}
