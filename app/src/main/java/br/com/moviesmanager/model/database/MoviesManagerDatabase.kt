package br.com.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.moviesmanager.model.dao.MovieDao
import br.com.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MoviesManagerDatabase: RoomDatabase() {
    companion object {
        const val MOVIE_DATABASE = "movieDatabase"
    }
    abstract fun getMovieDao(): MovieDao
}