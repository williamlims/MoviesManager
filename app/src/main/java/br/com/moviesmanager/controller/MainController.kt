package br.com.moviesmanager.controller

import androidx.room.Room
import br.com.moviesmanager.databinding.FragmentMainBinding
import br.com.moviesmanager.model.database.MoviesManagerDatabase
import br.com.moviesmanager.model.database.MoviesManagerDatabase.Companion.MOVIE_DATABASE
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.view.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainController(private val mainFragment: MainFragment) {
    private val movieDaoImpl = Room.databaseBuilder(
        mainFragment.requireContext(),
        MoviesManagerDatabase::class.java,
        MOVIE_DATABASE
    ).build().getMovieDao()

    fun insertMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.insertMovie(movie)
        }
    }

    fun getMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDaoImpl.selectMovies()
            //mainFragment.sele(movies)
            movies
        }
    }

    fun editMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.deleteMovie(movie)
        }
    }


}