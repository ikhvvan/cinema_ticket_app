package com.rizkimuhammadmukti.cinematicketapp.ui.seats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemSeatBinding

class SeatAdapter(
    private val seats: List<SeatSelectionActivity.Seat>,
    private val onSeatSelected: (SeatSelectionActivity.Seat, Boolean) -> Unit
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    private val selectedSeats = mutableSetOf<String>()

    inner class SeatViewHolder(private val binding: ItemSeatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(seat: SeatSelectionActivity.Seat) {
            with(binding) {
                // Set seat number with white text
                tvSeatLabel.text = seat.number
                tvSeatLabel.setTextColor(ContextCompat.getColor(itemView.context, R.color.seat_text))

                // Reset states
                btnSeat.isEnabled = !seat.isBooked
                btnSeat.isSelected = selectedSeats.contains(seat.number)

                // Apply background selector
                btnSeat.setBackgroundResource(R.drawable.bg_seat_selector)

                btnSeat.setOnClickListener {
                    if (!seat.isBooked) {
                        val isNowSelected = !selectedSeats.contains(seat.number)

                        if (isNowSelected) {
                            selectedSeats.add(seat.number)
                        } else {
                            selectedSeats.remove(seat.number)
                        }

                        // Update visual state
                        btnSeat.isSelected = isNowSelected
                        notifyItemChanged(adapterPosition)

                        onSeatSelected(seat, isNowSelected)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val binding = ItemSeatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(seats[position])
    }

    override fun getItemCount(): Int = seats.size

    fun clearSelection() {
        selectedSeats.clear()
        notifyDataSetChanged()
    }

    fun getSelectedSeats(): List<String> = selectedSeats.toList()
}