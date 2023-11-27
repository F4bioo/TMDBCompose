package com.fappslab.core.domain.usecase

import com.fappslab.core.domain.repository.FavoriteRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class DeleteFavoriteUseCaseTest {

    private val repository: FavoriteRepository = mockk()
    private val subject = DeleteFavoriteUseCase(repository)

    @Test
    fun `deleteFavorite Should delete a favorite movie When invoked`() = runTest {
        // Given
        coEvery { repository.deleteFavorite(any()) } just Runs

        // When
        subject(movie = mockk())

        // Then
        coVerify { repository.deleteFavorite(any()) }
    }
}
