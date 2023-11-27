package com.fappslab.core.domain.usecase

import com.fappslab.core.domain.repository.FavoriteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class IsFavoriteUseCaseTest {

    private val repository: FavoriteRepository = mockk()
    private val subject = IsFavoriteUseCase(repository)

    @Test
    fun `isFavorite Should return true When get result as a favorite`() = runTest {
        // Given
        val expectedResult = true
        coEvery { repository.isFavorite(any()) } returns true

        // When
        val result = subject(id = 1)

        // Then
        assertEquals(expectedResult, result)
        coVerify { repository.isFavorite(any()) }
    }

    @Test
    fun `isFavorite Should return false When get result as not a favorite`() = runTest {
        // Given
        val expectedResult = false
        coEvery { repository.isFavorite(any()) } returns false

        // When
        val result = subject(id = 1)

        // Then
        assertEquals(expectedResult, result)
        coVerify { repository.isFavorite(any()) }
    }
}
