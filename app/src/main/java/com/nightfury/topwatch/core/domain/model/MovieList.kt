package com.nightfury.topwatch.core.domain.model

data class MovieList(val list: List<Movie>)
data class Movie(
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String?,
    val rating: String?,
    val releaseDate: String?
)
