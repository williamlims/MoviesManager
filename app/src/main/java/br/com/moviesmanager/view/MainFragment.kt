package br.com.moviesmanager.view

import androidx.fragment.app.Fragment
import br.com.moviesmanager.databinding.FragmentMainBinding
import br.com.moviesmanager.model.entity.Movie

class MainFragment : Fragment(){
    private lateinit var fmb: FragmentMainBinding

    private val movieList: MutableList<Movie> = mutableListOf()

    private val moviesAdapter: MoviesAdapter by lazy {
        MovieAdapter(movieList, this)
    }

}