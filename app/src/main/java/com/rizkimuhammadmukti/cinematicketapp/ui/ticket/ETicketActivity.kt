package com.rizkimuhammadmukti.cinematicketapp.ui.ticket

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityEticketBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ETicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEticketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEticketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: ""
        val cinema = intent.getStringExtra("CINEMA") ?: ""
        val time = intent.getStringExtra("TIME") ?: ""
        val seats = intent.getStringArrayListExtra("SEATS") ?: emptyList<String>()
        val totalPrice = intent.getIntExtra("TOTAL_PRICE", 0)

        setupUI(movieTitle, cinema, time, seats, totalPrice)
        generateBarcode("CINEMA-${System.currentTimeMillis()}")
    }

    private fun setupUI(movieTitle: String, cinema: String, time: String, seats: List<String>, totalPrice: Int) {
        binding.tvMovieTitle.text = movieTitle
        binding.tvCinema.text = cinema
        binding.tvDateTime.text = time
        binding.tvSeats.text = seats.joinToString(", ")

        // Format current date
        val currentDate = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            .format(Date())
        binding.tvOrderDate.text = currentDate

        // Format price
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            .format(totalPrice.toLong())
            .replace("Rp", "Rp")
        binding.tvTotalPrice.text = formattedPrice

        binding.btnDone.setOnClickListener {
            finish() // Just finish this activity, not the entire app
        }
    }

    private fun generateBarcode(content: String) {
        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 400, 400)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            binding.ivBarcode.setImageBitmap(bmp)
        } catch (e: WriterException) {
            Toast.makeText(this, "Failed to generate barcode", Toast.LENGTH_SHORT).show()
        }
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
            val intent = Intent(context, ETicketActivity::class.java).apply {
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