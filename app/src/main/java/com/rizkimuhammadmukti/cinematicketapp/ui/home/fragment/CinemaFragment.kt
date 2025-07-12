package com.rizkimuhammadmukti.cinematicketapp.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkimuhammadmukti.cinematicketapp.data.repository.CinemaRepository
import com.rizkimuhammadmukti.cinematicketapp.databinding.FragmentCinemasBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter.CinemaAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CinemasFragment : Fragment() {
    private var _binding: FragmentCinemasBinding? = null
    private val binding get() = _binding!!
    private val cinemaRepository: CinemaRepository by inject()
    private lateinit var cinemaAdapter: CinemaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCinemasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCinemas()
    }

    private fun setupRecyclerView() {
        cinemaAdapter = CinemaAdapter { cinema ->
            // Handle cinema click - you can implement navigation or other actions here
            Log.d("CinemasFragment", "Clicked on cinema: ${cinema.name}")
        }

        binding.rvCinemas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cinemaAdapter
        }
    }

    private fun loadCinemas() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val cinemas = cinemaRepository.getCinemas()
                if (cinemas.isEmpty()) {
                    // Show empty state if needed
                    Log.d("CinemasFragment", "No cinemas found")
                } else {
                    cinemaAdapter.submitList(cinemas)
                }
            } catch (e: Exception) {
                Log.e("CinemasFragment", "Error loading cinemas", e)
                // Show error message to user if needed
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}