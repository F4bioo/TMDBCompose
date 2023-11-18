package com.fappslab.features.favorite.data.repository

import app.cash.turbine.test
import com.fappslab.core.data.local.dao.MovieDao
import com.fappslab.core.data.local.database.MovieDatabase
import com.fappslab.core.domain.repository.FavoriteRepository
import com.fappslab.features.favorite.data.source.FavoriteLocalDataSourceImpl
import com.fappslab.libraries.arch.testing.rules.LocalTestRule
import com.fappslab.libraries.arch.testing.rules.MainCoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class FavoriteRepositoryImplIntegrationTest {

    @get:Rule
    val remoteRule = LocalTestRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineTestRule()

    private lateinit var dao: MovieDao
    private lateinit var subject: FavoriteRepository

    @Before
    fun setUp() {
        dao = remoteRule.createTestDatabase<MovieDatabase>().movieDao()
        subject = FavoriteRepositoryImpl(
            localDataSource = FavoriteLocalDataSourceImpl(
                dao = dao,
                dispatcher = mainCoroutineRule.testDispatcher
            )
        )
    }

    @Test
    fun getFavorites_Should_returnListOfFavoriteMovies_When_moviesAreSetAsFavorite() = runTest {
        // Given
        val movie = EntityFactory.movie
        val expectedResult = listOf(movie)
        subject.setFavorite(movie)

        // When
        val result = subject.getFavorites()

        // Then
        result.test {
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun setFavorite_Should_insertMovieIntoFavorites_When_movieIsProvided() = runTest {
        // Given
        val movie = EntityFactory.movie

        // When
        subject.setFavorite(movie)

        // Then
        val result = dao.getFavorite(movie.id)
        assertNotNull(result)
    }

    @Test
    fun isFavorite_ShouldReturnTrue_When_getFavoriteMovie() = runTest {
        // Given
        val movie = EntityFactory.movie
        subject.setFavorite(movie)

        // When
        val result = subject.isFavorite(movie.id)

        // Then
        assertTrue(result)
    }

    @Test
    fun deleteFavorite_ShouldRemoveMovieFromFavorites_When_movieIsDeleted() = runTest {
        // Given
        val movie = EntityFactory.movie
        subject.setFavorite(movie)
        val beforeDelete = dao.getFavorite(movie.id)

        // When
        subject.deleteFavorite(movie)

        // Then
        val afterDelete = dao.getFavorite(movie.id)
        assertNotNull(beforeDelete)
        assertNull(afterDelete)
    }
}
