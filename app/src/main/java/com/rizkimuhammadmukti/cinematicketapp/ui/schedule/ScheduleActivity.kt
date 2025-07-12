@file:Suppress("DEPRECATION")

package com.rizkimuhammadmukti.cinematicketapp.ui.schedule

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityScheduleBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.seats.SeatSelectionActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private lateinit var movie: Movie
    private var selectedDate: String = ""
    private var selectedCinema: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get movie data from intent
        movie = intent.getParcelableExtra("MOVIE_DATA") ?: run {
            finish()
            return
        }

        // Set warna teks spinner awal
        binding.spinnerCinema.setPopupBackgroundResource(R.color.bg_bawah)

        setupUI()
        setupDateSelection()
        setupCinemaSpinner()
        setupShowtimes()
    }

    private fun setupUI() {
        // Set movie info
        Glide.with(this)
            .load(movie.posterUrl)
            .into(binding.ivMoviePoster)

        binding.tvMovieTitle.text = movie.title
        binding.tvMovieGenre.text = movie.genre
        binding.tvMovieDuration.text = movie.duration

        // Initially disable next button until time is selected
        binding.btnNext.isEnabled = false
        binding.btnNext.setOnClickListener {
            if (selectedTime.isNotEmpty()) {
                SeatSelectionActivity.start(
                    this,
                    movie.id,
                    movie.title,
                    selectedCinema,
                    selectedTime
                )
            } else {
                binding.tvSelectTime.error = "Please select a showtime"
            }
        }
    }

    private fun setupDateSelection() {
        val dateContainer = binding.llDateContainer
        val dates = getNext7Days()

        dateContainer.removeAllViews()

        dates.forEachIndexed { index, date ->
            val button = Button(this).apply {
                text = date
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8.dpToPx()
                }

                setBackgroundResource(R.drawable.bg_date_button)
                setTextColor(ContextCompat.getColor(context, R.color.text_primary))
                setPadding(16.dpToPx(), 8.dpToPx(), 16.dpToPx(), 8.dpToPx())

                isSelected = index == 0
                if (isSelected) selectedDate = date

                setOnClickListener {
                    for (i in 0 until dateContainer.childCount) {
                        dateContainer.getChildAt(i).isSelected = false
                    }
                    isSelected = true
                    selectedDate = date
                    updateShowtimes()
                }
            }
            dateContainer.addView(button)
        }
    }

    private fun setupCinemaSpinner() {
        val cinemas = listOf(
            "CGV Living Plaza",
            "XXI Chandstone",
            "Cinépolis Lippo Mall",
            "Flix Cinema Meikarta"
        )

        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item, // Gunakan layout custom
            cinemas
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item) // Gunakan dropdown layout custom
        binding.spinnerCinema.adapter = adapter

        binding.spinnerCinema.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Ubah warna teks yang terpilih menjadi putih
                (view as? TextView)?.setTextColor(ContextCompat.getColor(this@ScheduleActivity, R.color.white))
                selectedCinema = cinemas[position]
                updateShowtimes()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupShowtimes() {
        binding.rvShowtimes.layoutManager = GridLayoutManager(this, 3)
        binding.rvShowtimes.adapter = ShowtimeAdapter(movie.showTimes) { time ->
            selectedTime = time
            binding.btnNext.isEnabled = true
        }

        (binding.rvShowtimes.adapter as? ShowtimeAdapter)?.updateTimes(movie.showTimes)
    }

    private fun updateShowtimes() {
        (binding.rvShowtimes.adapter as? ShowtimeAdapter)?.let { adapter ->
            adapter.updateTimes(movie.showTimes)
            selectedTime = ""
            binding.btnNext.isEnabled = false
        }
    }

    private fun getNext7Days(): List<String> {
        val dates = ArrayList<String>()
        val dateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(7) {
            dates.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return dates
    }

    companion object {
        fun start(context: Context, movie: Movie) {
            val intent = Intent(context, ScheduleActivity::class.java).apply {
                putExtra("MOVIE_DATA", movie)
            }
            context.startActivity(intent)
        }
    }
}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()