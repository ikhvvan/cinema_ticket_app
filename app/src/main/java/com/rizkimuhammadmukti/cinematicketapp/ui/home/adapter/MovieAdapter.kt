package com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemMovieBinding
import com.rizkimuhammadmukti.cinematicketapp.databinding.ItemLoadingBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.schedule.ScheduleActivity

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding = ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MovieViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                val movie = getItem(position)
                if (movie != null) {
                    holder.bind(movie)
                }
            }
            is LoadingViewHolder -> {
                // No binding needed for loading view
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoading) 1 else 0
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
        if (loading) {
            notifyItemInserted(itemCount)
        } else {
            notifyItemRemoved(itemCount)
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                // Load movie poster
                Glide.with(root.context)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .error(R.drawable.ic_movie_error)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPoster)

                // Set movie details
                tvTitle.text = movie.title
                tvGenre.text = movie.genre
                rbRating.rating = movie.rating / 2 // Convert 10-point to 5-star
                tvRating.text = root.context.getString(R.string.rating_format, movie.rating)

                // Set content description for accessibility
                ivPoster.contentDescription = root.context.getString(
                    R.string.movie_poster_desc,
                    movie.title
                )

                // Set click listener for booking button
                btnPesanTiket.setOnClickListener {
                    // Navigate to schedule activity
                    ScheduleActivity.start(root.context, movie)
                }

                // Optional: Set click listener for the whole item
                root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

    inner class LoadingViewHolder(
        binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}