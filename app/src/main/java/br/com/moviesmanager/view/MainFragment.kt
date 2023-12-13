package br.com.moviesmanager.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.moviesmanager.controller.MainController
import br.com.moviesmanager.databinding.FragmentMainBinding
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.model.entity.Movie.Companion.WATCHED_FALSE
import br.com.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import br.com.moviesmanager.view.adapter.MovieAdapter
import br.com.moviesmanager.view.adapter.OnMovieClickListener

class MainFragment : Fragment(), OnMovieClickListener {
    private lateinit var fmb: FragmentMainBinding

    private val movieList: MutableList<Movie> = mutableListOf()

    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val MOVIE_FRAGMENT_REQUEST_KEY = "MOVIE_FRAGMENT_REQUEST_KEY"
    }

    private val mainController: MainController by lazy {
        MainController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(MOVIE_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == MOVIE_FRAGMENT_REQUEST_KEY) {
                val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(EXTRA_MOVIE, Movie::class.java)
                } else {
                    bundle.getParcelable(EXTRA_MOVIE)
                }
                movie?.also { receivedMovie ->
                    movieList.indexOfFirst { it.id == receivedMovie.id }.also { position ->
                        if (position != -1) {
                            mainController.editMovie(receivedMovie)
                            movieList[position] = receivedMovie
                            moviesAdapter.notifyItemChanged(position)
                        } else {
                            mainController.insertMovie(receivedMovie)
                            movieList.add(receivedMovie)
                            moviesAdapter.notifyItemInserted(movieList.lastIndex)
                        }
                    }
                }

                // Hiding soft keyboard
                (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    fmb.root.windowToken,
                    HIDE_NOT_ALWAYS
                )
            }
        }
        mainController.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Lista de filmes"

        fmb = FragmentMainBinding.inflate(inflater, container, false).apply {
            moviesRv.layoutManager = LinearLayoutManager(context)
            moviesRv.adapter = moviesAdapter

            addMovieFab.setOnClickListener {
                navController.navigate(
                    MainFragmentDirections.actionMainFragmentToMovieFragment(null, editMovie = false)
                )
            }
        }
        return fmb.root
    }

    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    override fun onRemoveMovieMenuItemClick(position: Int) {
        mainController.deleteMovie(movieList[position])
        movieList.removeAt(position)
        moviesAdapter.notifyItemRemoved(position)
    }

    override fun onEditMovieMenuItemClick(position: Int) = navigateToMovieFragment(position, true)

    override fun onWatchedCheckBoxClick(position: Int, checked: Boolean) {
        movieList[position].apply {
            watched = if (checked) WATCHED_TRUE else WATCHED_FALSE
        }
    }

    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                MainFragmentDirections.actionMainFragmentToMovieFragment(it, editMovie)
            )
        }
    }

}