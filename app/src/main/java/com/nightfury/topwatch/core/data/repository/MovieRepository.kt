package com.nightfury.topwatch.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.nightfury.topwatch.core.data.model.toDomain
import com.nightfury.topwatch.core.data.repository.datasource.MovieDataSource
import com.nightfury.topwatch.core.domain.model.Movie
import com.nightfury.topwatch.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImp(private val movieDataSource: MovieDataSource) : MovieRepository {

    override suspend fun getTopMovies(): Flow<PagingData<Movie>> {
        return movieDataSource.getTopMovie().map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun getMovie(movieId: Long): Movie? {
        return movieDataSource.getMovie(movieId)?.toDomain()
    }
}