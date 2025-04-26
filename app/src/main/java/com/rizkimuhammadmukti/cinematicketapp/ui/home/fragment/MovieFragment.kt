package com.rizkimuhammadmukti.cinematicketapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rizkimuhammadmukti.cinematicketapp.data.repository.MovieRepository
import com.rizkimuhammadmukti.cinematicketapp.databinding.FragmentMovieBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter.MovieAdapter
import org.koin.android.ext.android.inject

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieRepository: MovieRepository by inject()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadMovies()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            // Handle movie click
        }

        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }

    private fun loadMovies() {
        // Use coroutines to load movies
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}