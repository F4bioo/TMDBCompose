package com.fappslab.features.detail.domain.usecase

import androidx.paging.PagingData
import com.fappslab.features.detail.domain.model.Pack
import com.fappslab.features.detail.domain.repository.DetailRepository
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.libraries.design.component.preview.moviesDataPreview
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class GetMovieDetailUseCaseTest {

    private val repository = mockk<DetailRepository>()
    private val subject = GetMovieDetailUseCase(repository)

    @Test
    fun `getMovie Should return Pack object When invoked`() = runTest {
        // Given
        val detail = detailDataPreview()
        val movies = flowOf(PagingData.from(moviesDataPreview()))
        val expectedResult = Pack(detail, movies)
        coEvery { repository.getMovieDetail(any()) } returns detail
        every { repository.getSimilarMovies(any()) } returns movies

        // When
        val result = subject(id = 1)

        // Then
        assertEquals(expectedResult, result)
        coVerify { repository.getMovieDetail(any()) }
        verify { repository.getSimilarMovies(any()) }
    }
}
