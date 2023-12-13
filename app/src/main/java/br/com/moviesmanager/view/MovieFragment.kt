package br.com.moviesmanager.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.moviesmanager.R
import br.com.moviesmanager.databinding.FragmentMovieBinding
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.model.entity.Movie.Companion.WATCHED_FALSE
import br.com.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import br.com.moviesmanager.view.MainFragment.Companion.EXTRA_MOVIE
import br.com.moviesmanager.view.MainFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY

class MovieFragment : Fragment() {
    private lateinit var ftb: FragmentMovieBinding
    private val navigationArgs: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.movie_details)

        ftb = FragmentMovieBinding.inflate(inflater, container, false)

        val receivedMovie = navigationArgs.movie
        receivedMovie?.also { movie ->
            with(ftb) {
                nameEt.setText(movie.name)
                releasedEt.setText(movie.released)
                producerEt.setText(movie.producer)
                durationEt.setText(movie.duration)
                watchedCb.isChecked = movie.watched == WATCHED_TRUE
                noteEt.setText(movie.note)

                ArrayAdapter.createFromResource(
                    genderSP.context,
                    R.array.gender_array,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                    // Apply the adapter to the spinner
                    genderSP.adapter = adapter
                }

                navigationArgs.editMovie.also { editMovie ->
                    nameEt.isEnabled = editMovie
                    releasedEt.isEnabled = editMovie
                    producerEt.isEnabled = editMovie
                    durationEt.isEnabled = editMovie
                    watchedCb.isEnabled = editMovie
                    noteEt.isEnabled = editMovie
                    saveBt.visibility = if (editMovie) VISIBLE else GONE
                }
            }
        }

        ftb.run {
            saveBt.setOnClickListener {
                setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_MOVIE, Movie(
                            receivedMovie?.id ?: 0,
                            nameEt.text.toString(),
                            producerEt.text.toString(),
                            durationEt.text.toString(),
                            if (watchedCb.isChecked) WATCHED_TRUE else WATCHED_FALSE,
                            noteEt.text.toString().toInt(),
                            genderSP.selectedItem.toString().toInt()
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }

        return ftb.root
    }
}
