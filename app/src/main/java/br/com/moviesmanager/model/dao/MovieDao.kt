package br.com.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.moviesmanager.model.entity.Movie

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie"
    }

    @Insert
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun selectMovies(): List<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY name")
    fun selectMoviesByName(): List<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY note DESC")
    fun selectMoviesByNote(): List<Movie>
}