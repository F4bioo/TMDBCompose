package com.fappslab.features.search.data.repository

import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.fappslab.features.search.data.source.remote.SearchRemoteDataSourceImpl
import com.fappslab.features.search.domain.repository.SearchRepository
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import com.fappslab.libraries.arch.testing.rules.RemoteTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

internal class SearchRepositoryImplIntegrationTest {

    @get:Rule
    val remoteRule = RemoteTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var subject: SearchRepository

    @Before
    fun setUp() {
        subject = SearchRepositoryImpl(
            remoteDataSource = SearchRemoteDataSourceImpl(
                service = remoteRule.createTestService()
            ),
            config = PagingConfig(pageSize = 20)
        )
    }

    @Test
    fun `getMovies Should validate flow data creation When invoked`() = runTest {
        // Given
        remoteRule.toServerSuccessResponse { SEARCH_SUCCESS_RESPONSE }

        // When
        val result = subject.getSearchMovie(query = "Avatar")

        // Then
        result.test {
            assertNotNull(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
