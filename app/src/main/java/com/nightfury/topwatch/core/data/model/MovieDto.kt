package com.nightfury.topwatch.core.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "top_movies")
data class MovieDto(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @SerializedName("id")
    val movieId: Long,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val rating: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
) : Serializable

data class MovieList(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val movie: List<MovieDto>
)