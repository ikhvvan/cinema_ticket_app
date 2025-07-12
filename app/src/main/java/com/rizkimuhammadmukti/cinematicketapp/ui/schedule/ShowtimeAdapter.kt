package com.rizkimuhammadmukti.cinematicketapp.ui.schedule

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemShowtimeBinding

class ShowtimeAdapter(
    private var times: List<String>,
    private val onTimeSelected: (String) -> Unit
) : RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ShowtimeViewHolder(
        private val binding: ItemShowtimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(time: String, isSelected: Boolean) {
            with(binding.btnShowtime) {
                text = time

                // Update visual states
                if (isSelected) {
                    // Selected state
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    background = ContextCompat.getDrawable(context, R.drawable.bg_showtime_selected)
                } else {
                    // Default state
                    setTextColor(ContextCompat.getColor(context, R.color.text_login))
                    background = ContextCompat.getDrawable(context, R.drawable.bg_showtime_normal)
                }

                setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        lastSelectedPosition = selectedPosition
                        selectedPosition = adapterPosition

                        // Notify previous and new selections
                        notifyItemChanged(lastSelectedPosition)
                        notifyItemChanged(selectedPosition)

                        onTimeSelected(time)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowtimeViewHolder {
        val binding = ItemShowtimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowtimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowtimeViewHolder, position: Int) {
        holder.bind(times[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = times.size

    fun updateTimes(newTimes: List<String>) {
        times = newTimes
        lastSelectedPosition = selectedPosition
        selectedPosition = -1
        notifyDataSetChanged()
    }

    fun getSelectedTime(): String? {
        return if (selectedPosition in 0 until times.size) {
            times[selectedPosition]
        } else {
            null
        }
    }
}