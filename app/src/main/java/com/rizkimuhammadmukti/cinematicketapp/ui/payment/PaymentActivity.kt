package com.rizkimuhammadmukti.cinematicketapp.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityPaymentBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.ticket.ETicketActivity
import java.text.NumberFormat
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: ""
        val cinema = intent.getStringExtra("CINEMA") ?: ""
        val time = intent.getStringExtra("TIME") ?: ""
        val seats = intent.getStringArrayListExtra("SEATS") ?: emptyList<String>()
        val totalPrice = intent.getIntExtra("TOTAL_PRICE", 0)

        setupUI(movieTitle, cinema, time, seats, totalPrice)
        setupButtons()
    }

    private fun setupUI(movieTitle: String, cinema: String, time: String, seats: List<String>, totalPrice: Int) {
        // Set movie information
        binding.tvMovieTitle.text = movieTitle
        binding.tvCinema.text = cinema
        binding.tvDateTime.text = time

        // Set seats information
        binding.tvSeats.text = seats.joinToString(", ")

        // Format and display total price
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            .format(totalPrice.toLong())
            .replace("Rp", "Rp")
        binding.tvTotalPrice.text = formattedPrice

        // Initialize payment success message as hidden
        binding.paymentSuccess.visibility = View.GONE
    }

    private fun setupButtons() {
        // Back button returns to seat selection
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Pay Now button processes payment
        binding.btnPay.setOnClickListener {
            processPayment()
        }
    }

    private fun processPayment() {
        // Show processing UI
        binding.btnPay.isEnabled = false
        binding.btnPay.text = "Processing..."

        // Simulate payment processing (2 seconds delay)
        Handler(Looper.getMainLooper()).postDelayed({
            // Show success message
            binding.paymentSuccess.visibility = View.VISIBLE

            // After another 1.5 seconds, navigate to e-ticket
            Handler(Looper.getMainLooper()).postDelayed({
                navigateToETicket()
            }, 1500)
        }, 2000)
    }

    private fun navigateToETicket() {
        ETicketActivity.start(
            this,
            intent.getStringExtra("MOVIE_TITLE") ?: "",
            intent.getStringExtra("CINEMA") ?: "",
            intent.getStringExtra("TIME") ?: "",
            intent.getStringArrayListExtra("SEATS") ?: emptyList(),
            intent.getIntExtra("TOTAL_PRICE", 0)
        )

        // Finish this activity so user can't go back
        finish()
    }

    companion object {
        fun start(
            context: Context,
            movieTitle: String,
            cinema: String,
            time: String,
            seats: List<String>,
            totalPrice: Int
        ) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("MOVIE_TITLE", movieTitle)
                putExtra("CINEMA", cinema)
                putExtra("TIME", time)
                putStringArrayListExtra("SEATS", ArrayList(seats))
                putExtra("TOTAL_PRICE", totalPrice)
            }
            context.startActivity(intent)
        }
    }
}