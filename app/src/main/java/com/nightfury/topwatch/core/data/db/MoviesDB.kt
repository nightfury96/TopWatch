package com.nightfury.topwatch.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nightfury.topwatch.core.data.model.MovieDto

@Database(
    entities = [MovieDto::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}