package com.nightfury.topwatch.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nightfury.topwatch.core.data.model.MovieDto

@Dao
interface MovieDao {
    @Query("SELECT * FROM top_movies")
    fun getAllMovies(): PagingSource<Int, MovieDto>

    @Query("SELECT * FROM top_movies where id=:movieId")
    fun getMovie(movieId: Long): MovieDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMovies(movies: List<MovieDto>)

    @Query("DELETE FROM top_movies")
    suspend fun deleteAllMovies()
}