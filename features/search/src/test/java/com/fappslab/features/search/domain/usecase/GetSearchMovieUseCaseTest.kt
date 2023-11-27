package com.fappslab.features.search.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.fappslab.features.search.domain.repository.SearchRepository
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class GetSearchMovieUseCaseTest {

    private val repository: SearchRepository = mockk()
    private val subject = GetSearchMovieUseCase(repository)

    @Test
    fun `getMovies Should return movie flow object When invoked`() = runTest {
        // Given
        val expectedResult = PagingData.from(moviesDataPreview())
        coEvery { repository.getSearchMovie(any()) } returns flowOf(expectedResult)

        // When
        val result = subject(query = "Avatar")

        // Then
        result.test {
            assertEquals(expectedResult, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        coVerify { repository.getSearchMovie(any()) }
    }
}
