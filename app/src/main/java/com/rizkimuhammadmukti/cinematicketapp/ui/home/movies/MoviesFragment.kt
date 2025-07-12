package com.rizkimuhammadmukti.cinematicketapp.ui.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.databinding.FragmentMoviesBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter.MovieAdapter
import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import com.rizkimuhammadmukti.cinematicketapp.ui.schedule.ScheduleActivity
import com.rizkimuhammadmukti.cinematicketapp.utils.SharedPrefs

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefs = SharedPrefs(requireContext())
        setupRecyclerView()
        loadMovies()
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            // Handle item click (optional)
            ScheduleActivity.start(requireContext(), movie)
        }

        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadMovies() {
        // Show loading state
        movieAdapter.setLoading(true)
        binding.swipeRefresh.isRefreshing = true

        // In a real app, you would fetch this from a ViewModel/Repository
        val dummyMovies = listOf(
            Movie(
                id = "1",
                title = "Avengers: Endgame",
                genre = "Action, Adventure, Sci-Fi",
                duration = "3h 1m",
                rating = 8.4f,
                posterUrl = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                synopsis = "After the devastating events of Avengers: Infinity War, the universe is in ruins.",
                showTimes = listOf("10:00", "13:30", "17:00", "20:30")
            ),
            Movie(
                id = "2",
                title = "The Dark Knight",
                genre = "Action, Crime, Drama",
                duration = "2h 32m",
                rating = 9.0f,
                posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                synopsis = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.",
                showTimes = listOf("11:00", "14:30", "18:00", "21:30")
            )
        )

        // Simulate network delay
        binding.rvMovies.postDelayed({
            movieAdapter.submitList(dummyMovies)
            movieAdapter.setLoading(false)
            binding.swipeRefresh.isRefreshing = false
        }, 1500)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadMovies()
        }

        // Customize the refresh indicator colors
        binding.swipeRefresh.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorAccent,
            R.color.colorPrimaryDark
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
