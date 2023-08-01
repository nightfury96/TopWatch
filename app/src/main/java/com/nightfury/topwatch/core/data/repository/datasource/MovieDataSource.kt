package com.nightfury.topwatch.core.data.repository.datasource

import androidx.paging.PagingData
import com.nightfury.topwatch.core.data.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    fun getTopMovie(): Flow<PagingData<MovieDto>>
    fun getMovie(movieId: Long): MovieDto?
}