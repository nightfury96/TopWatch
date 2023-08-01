package com.nightfury.topwatch.core.data.repository.datasource

import androidx.paging.PagingData
import com.nightfury.topwatch.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    fun getTopMovie(): Flow<PagingData<Movie>>
    fun getMovie(movieId: Long): Movie?
}