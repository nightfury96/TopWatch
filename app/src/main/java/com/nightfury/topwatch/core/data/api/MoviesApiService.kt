package com.nightfury.topwatch.core.data.api

import com.nightfury.topwatch.core.data.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String,
        @Query("page")
        pageNumber: Int
    ): Response<MovieList>
}