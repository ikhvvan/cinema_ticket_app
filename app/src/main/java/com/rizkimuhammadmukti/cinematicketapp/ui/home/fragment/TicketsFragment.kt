package com.rizkimuhammadmukti.cinematicketapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkimuhammadmukti.cinematicketapp.data.model.Ticket
import com.rizkimuhammadmukti.cinematicketapp.data.repository.TicketRepository
import com.rizkimuhammadmukti.cinematicketapp.databinding.FragmentTicketsBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter.TicketAdapter
import com.rizkimuhammadmukti.cinematicketapp.ui.ticket.ETicketActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketsFragment : Fragment() {
    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = _binding!!
    private val ticketRepository: TicketRepository by inject()
    private lateinit var ticketAdapter: TicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadTickets()
    }

    private fun setupRecyclerView() {
        ticketAdapter = TicketAdapter { ticket ->
            showTicketDetails(ticket)
        }

        binding.rvTickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadTickets() {
        lifecycleScope.launch {
            val tickets = ticketRepository.getUserTickets()
            ticketAdapter.submitList(tickets)
        }
    }

    private fun showTicketDetails(ticket: Ticket) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM - HH:mm", Locale.getDefault())
        val date = inputFormat.parse(ticket.showtime)
        val formattedTime = outputFormat.format(date ?: Date())

        ETicketActivity.start(
            requireContext(),
            ticket.movieTitle,
            ticket.cinemaName,
            formattedTime,
            listOf(ticket.seatNumber),
            ticket.totalPrice.toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}