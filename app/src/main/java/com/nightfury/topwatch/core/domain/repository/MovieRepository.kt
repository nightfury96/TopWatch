package com.nightfury.topwatch.core.domain.repository

import androidx.paging.PagingData
import com.nightfury.topwatch.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopMovies(): Flow<PagingData<Movie>>
    suspend fun getMovie(movieId: Long): Movie?
}