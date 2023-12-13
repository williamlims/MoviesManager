package br.com.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.moviesmanager.R
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.databinding.TileMovieBinding
import br.com.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.TaskTileViewHolder>() {
    inner class TaskTileViewHolder(tileTaskBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileTaskBinding.root) {
        val nameTv: TextView = tileTaskBinding.nameTV
        val releasedTV: TextView = tileTaskBinding.releasedTV
        val producerTV: TextView = tileTaskBinding.producerTV
        val durationTV: TextView = tileTaskBinding.durationTV
        val watchedCb: CheckBox = tileTaskBinding.watchedCb
        val noteTV: TextView = tileTaskBinding.noteTV
        val genderSP: Spinner = tileTaskBinding.genderSP

        init {
            tileTaskBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_movie,
                            menu
                        )
                        menu?.findItem(R.id.removeMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
                watchedCb.run {
                    setOnClickListener {
                        onMovieClickListener.onWatchedCheckBoxClick(adapterPosition, isChecked)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { TaskTileViewHolder(this) }


    override fun onBindViewHolder(holder: TaskTileViewHolder, position: Int) {
        movieList[position].let { movie ->
            with(holder) {
                nameTv.text = movie.name
                releasedTV.text = movie.released
                producerTV.text = movie.producer
                durationTV.text = movie.duration.toString()
                watchedCb.isChecked = movie.watched == WATCHED_TRUE
                noteTV.text = movie.note.toString()
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
            }
        }
    }

    override fun getItemCount() = movieList.size
}