package com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rizkimuhammadmukti.cinematicketapp.data.model.Ticket
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemTicketBinding
import java.text.SimpleDateFormat
import java.util.*

class TicketAdapter(
    private val onItemClick: (Ticket) -> Unit
) : ListAdapter<Ticket, TicketAdapter.TicketViewHolder>(TicketDiffCallback()) {

    inner class TicketViewHolder(
        private val binding: ItemTicketBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket) {
            with(binding) {
                tvMovieTitle.text = ticket.movieTitle
                tvCinemaName.text = ticket.cinemaName
                tvSeatNumber.text = ticket.seatNumber

                // Format the showtime
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                val date = inputFormat.parse(ticket.showtime)
                tvShowtime.text = outputFormat.format(date ?: Date())

                root.setOnClickListener { onItemClick(ticket) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }
    }
}