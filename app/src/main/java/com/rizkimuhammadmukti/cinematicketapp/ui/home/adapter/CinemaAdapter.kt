package com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkimuhammadmukti.cinematicketapp.data.model.Cinema
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemCinemaBinding

class CinemaAdapter(
    private val onItemClick: (Cinema) -> Unit
) : ListAdapter<Cinema, CinemaAdapter.CinemaViewHolder>(CinemaDiffCallback()) {

    inner class CinemaViewHolder(
        private val binding: ItemCinemaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cinema: Cinema) {
            with(binding) {
                tvCinemaName.text = cinema.name
                tvCinemaAddress.text = "${cinema.address}, ${cinema.city}"

                // Load cinema logo using Glide
                Glide.with(root.context)
                    .load(cinema.logoUrl)
                    .centerCrop()
                    .into(ivCinemaLogo)

                root.setOnClickListener {
                    onItemClick(cinema)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val binding = ItemCinemaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CinemaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class CinemaDiffCallback : DiffUtil.ItemCallback<Cinema>() {
        override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
            return oldItem == newItem
        }
    }
}