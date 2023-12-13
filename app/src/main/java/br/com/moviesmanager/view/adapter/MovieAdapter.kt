package br.com.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.moviesmanager.R
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.databinding.TileMovieBinding

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.TaskTileViewHolder>() {
    inner class TaskTileViewHolder(tileTaskBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileTaskBinding.root) {
        val nameTv: TextView = tileTaskBinding.nameTv
        val doneCb: CheckBox = tileTaskBinding.doneCb

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
                doneCb.run {
                    setOnClickListener {
                        onMovieClickListener.onDoneCheckBoxClick(adapterPosition, isChecked)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { TaskTileViewHolder(this) }


    override fun onBindViewHolder(holder: TaskTileViewHolder, position: Int) {
        movieList[position].let { task ->
            with(holder) {
                nameTv.text = task.name
                doneCb.isChecked = task.done == MOVIE_DONE_TRUE
            }
        }
    }

    override fun getItemCount() = taskList.size
}