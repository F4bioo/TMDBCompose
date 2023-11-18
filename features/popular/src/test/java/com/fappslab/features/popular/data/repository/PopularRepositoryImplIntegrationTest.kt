package com.fappslab.features.popular.data.repository

import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.fappslab.features.popular.data.source.remote.PopularRemoteDataSourceImpl
import com.fappslab.features.popular.domain.repository.PopularRepository
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.arch.testing.rules.RemoteTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

internal class PopularRepositoryImplIntegrationTest {

    @get:Rule
    val remoteRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var subject: PopularRepository

    @Before
    fun setUp() {
        subject = PopularRepositoryImpl(
            remoteDataSource = PopularRemoteDataSourceImpl(
                service = remoteRule.createTestService()
            ),
            config = PagingConfig(pageSize = 20)
        )
    }

    @Test
    fun `getMovies Should validate flow data creation When invoked`() = runTest {
        // Given
        remoteRule.toServerSuccessResponse { POPULAR_SUCCESS_RESPONSE }

        // When
        val result = subject.getMovies()

        // Then
        result.test {
            assertNotNull(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
