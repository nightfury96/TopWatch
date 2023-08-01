package com.nightfury.topwatch.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nightfury.topwatch.core.domain.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM top_movies")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM top_movies where movieId=:movieId ")
    fun getMovies(movieId: Long): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMovies(movies: List<Movie>)

    @Query("DELETE FROM top_movies")
    suspend fun deleteAllMovies()
}