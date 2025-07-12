package com.rizkimuhammadmukti.cinematicketapp.ui.seats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivitySeatSelectionBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.payment.PaymentActivity

class SeatSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatSelectionBinding
    private lateinit var seatAdapter: SeatAdapter
    private val selectedSeats = mutableListOf<String>()
    private val seatPrice = 45000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: ""
        val cinema = intent.getStringExtra("CINEMA") ?: ""
        val time = intent.getStringExtra("TIME") ?: ""

        setupUI(movieTitle, cinema, time)
        setupSeats()
        setupButton()
    }

    private fun setupUI(movieTitle: String, cinema: String, time: String) {
        binding.tvMovieTitle.text = movieTitle
        binding.tvCinema.text = cinema
        binding.tvDateTime.text = time
        binding.btnConfirm.text = getString(R.string.confirm_selection)
    }

    private fun setupSeats() {
        val seats = mutableListOf<Seat>()
        val rows = listOf("A", "B", "C", "D", "E")

        rows.forEach { row ->
            for (col in 1..10) {
                val seatNumber = "$row$col"
                seats.add(Seat(seatNumber, (1..5).random() == 1))
            }
        }

        seatAdapter = SeatAdapter(seats) { seat, isSelected ->
            if (isSelected) selectedSeats.add(seat.number)
            else selectedSeats.remove(seat.number)
            updateSelectionInfo()
        }

        binding.rvSeats.layoutManager = GridLayoutManager(this, 10)
        binding.rvSeats.adapter = seatAdapter
    }

    private fun updateSelectionInfo() {
        binding.tvSelectedSeats.text = "Selected: ${selectedSeats.joinToString(", ")}"
        binding.tvTotalPrice.text = "Total: Rp${selectedSeats.size * seatPrice}"
        binding.btnConfirm.isEnabled = selectedSeats.isNotEmpty()
    }

    private fun setupButton() {
        binding.btnConfirm.setOnClickListener {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, R.string.select_seats_first, Toast.LENGTH_SHORT).show()
            } else {
                PaymentActivity.start(
                    this,
                    intent.getStringExtra("MOVIE_TITLE") ?: "",
                    intent.getStringExtra("CINEMA") ?: "",
                    intent.getStringExtra("TIME") ?: "",
                    selectedSeats,
                    selectedSeats.size * seatPrice
                )
            }
        }
    }

    data class Seat(
        val number: String,
        val isBooked: Boolean = false
    )

    companion object {
        fun start(context: Context, movieId: String, movieTitle: String, cinema: String, time: String) {
            val intent = Intent(context, SeatSelectionActivity::class.java).apply {
                putExtra("MOVIE_ID", movieId)
                putExtra("MOVIE_TITLE", movieTitle)
                putExtra("CINEMA", cinema)
                putExtra("TIME", time)
            }
            context.startActivity(intent)
        }
    }
}