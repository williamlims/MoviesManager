package br.com.moviesmanager.controller

import br.com.moviesmanager.databinding.FragmentMainBinding
import br.com.moviesmanager.model.entity.Movie
import br.com.moviesmanager.view.MainFragment


class MainController(private val mainFragment: MainFragment) {
    private lateinit var fmb: FragmentMainBinding

    private val movieList: MutableList<Movie> = mutableListOf()


}