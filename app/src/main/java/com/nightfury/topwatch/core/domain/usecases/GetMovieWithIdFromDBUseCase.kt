package com.nightfury.topwatch.core.domain.usecases

import com.nightfury.topwatch.core.domain.repository.MovieRepository

class GetMovieWithIdFromDBUseCase(private val movieRepository: MovieRepository) {
    suspend fun run(movieId: Long) = movieRepository.getMovie(movieId)
}