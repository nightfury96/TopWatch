package com.nightfury.topwatch.core.domain.usecases

import com.nightfury.topwatch.core.domain.repository.MovieRepository

class GetTopMoviesFromDBUseCase(private val movieRepository: MovieRepository) {
    suspend fun run() = movieRepository.getTopMovies()
}