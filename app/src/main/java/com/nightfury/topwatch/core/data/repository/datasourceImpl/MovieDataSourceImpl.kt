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
import com.nightfury.topwatch.core.data.repository.datasource.MovieDataSource
import com.nightfury.topwatch.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MovieDataSourceImpl(
    private val moviesApiService: MoviesApiService,
    private val apiKey: String,
    moviesDB: MoviesDB
) : MovieDataSource {
    val movieDao = moviesDB.movieDao()
    override fun getTopMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = { movieDao.getAllMovies() },
            remoteMediator = MovieMediator()
        ).flow
    }

    override fun getMovie(movieId: Long): Movie? {
        return movieDao.getMovies(movieId)
    }

    private inner class MovieMediator : RemoteMediator<Int, Movie>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, Movie>
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
                    val movies = req.body()
                    if (movies != null) {
                        movieDao.insertOrUpdateMovies(movies.list)
                        MediatorResult.Success(movies.list.size < state.config.pageSize)
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