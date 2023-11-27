package com.fappslab.features.detail.data.repository

import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.fappslab.features.detail.data.source.remote.DetailRemoteDataSourceImpl
import com.fappslab.features.detail.domain.repository.DetailRepository
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.arch.testing.rules.RemoteTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
internal class DetailRepositoryImplIntegrationTest {

    @get:Rule
    val remoteRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var subject: DetailRepository

    @Before
    fun setUp() {
        subject = DetailRepositoryImpl(
            remoteDataSource = DetailRemoteDataSourceImpl(
                service = remoteRule.createTestService(),
            ),
            config = PagingConfig(pageSize = 20)
        )
    }

    @Test
    fun `getMovies Should return detail object When invoked`() {
        runTest {
            // Given
            val expectedResult = ResponseFactory.detail
            remoteRule.toServerSuccessResponse { DETAIL_SUCCESS_RESPONSE }

            // When

            val result = subject.getMovieDetail(id = 100)

            // Then
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getSimilar Should validate flow data creation When invoked`() = runTest {
        // Given
        remoteRule.toServerSuccessResponse { SIMILAR_SUCCESS_RESPONSE }

        // When
        val result = subject.getSimilarMovies(id = 100)

        // Then
        result.test {
            assertNotNull(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
