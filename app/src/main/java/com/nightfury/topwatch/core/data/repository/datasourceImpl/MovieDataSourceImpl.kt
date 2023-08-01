package com.nightfury.topwatch.core.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.nightfury.topwatch.core.data.api.MoviesApiService
import com.nightfury.topwatch.core.data.db.MoviesDB
import com.nightfury.topwatch.core.data.model.MovieDto
import com.nightfury.topwatch.core.data.repository.datasource.MovieDataSource
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MovieDataSourceImpl(
    private val moviesApiService: MoviesApiService,
    private val apiKey: String,
    moviesDB: MoviesDB
) : MovieDataSource {
    val movieDao = moviesDB.movieDao()
    override fun getTopMovie(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = { movieDao.getAllMovies() },
            remoteMediator = MovieMediator()
        ).flow
    }

    override fun getMovie(movieId: Long): MovieDto? {
        return movieDao.getMovie(movieId)
    }

    private inner class MovieMediator : RemoteMediator<Int, MovieDto>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, MovieDto>
        ): MediatorResult {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.APPEND -> {
                    state.pages.size + 1
                }

                else -> {
                    return MediatorResult.Success(true)
                }
            }
            return try {
                val req = moviesApiService.getPopularMovies(apiKey, pageNumber)
                if (req.isSuccessful) {
                    val popularMovies = req.body()
                    if (popularMovies != null) {
                        movieDao.insertOrUpdateMovies(popularMovies.movies)
                        MediatorResult.Success(popularMovies.movies.size < state.config.pageSize)
                    }
                }
                MediatorResult.Error(Exception("Not successful request with status: ${req.code()} ${req.errorBody()}"))
            } catch (e: Exception) {
                e.printStackTrace()
                MediatorResult.Error(e)
            }
        }
    }
}