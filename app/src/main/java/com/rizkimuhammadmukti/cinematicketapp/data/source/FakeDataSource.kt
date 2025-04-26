package com.rizkimuhammadmukti.cinematicketapp.data.source

import com.rizkimuhammadmukti.cinematicketapp.data.model.Cinema
import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import kotlinx.coroutines.delay

class FakeDataSource {
    suspend fun getNowPlayingMovies(): List<Movie> {
        delay(1000) // Simulate network delay
        return listOf(
            Movie(
                id = "1",
                title = "Avengers: Endgame",
                genre = "Action, Adventure, Sci-Fi",
                duration = "3h 1m",
                rating = 8.4f,
                posterUrl = "https://example.com/avengers.jpg",
                synopsis = "After the devastating events of Avengers: Infinity War, the universe is in ruins.",
                showTimes = listOf("10:00", "13:30", "17:00", "20:30")
            ),
            // Add more movies...
        )
    }

    suspend fun getComingSoonMovies(): List<Movie> {
        delay(1000)
        return listOf(
            Movie(
                id = "4",
                title = "Black Widow",
                genre = "Action, Adventure, Sci-Fi",
                duration = "2h 14m",
                rating = 7.2f,
                posterUrl = "https://example.com/blackwidow.jpg",
                synopsis = "Natasha Romanoff confronts the darker parts of her ledger.",
                showTimes = emptyList()
            ),
            // Add more movies...
        )
    }

    suspend fun getMovieById(id: String): Movie? {
        delay(500)
        return getNowPlayingMovies().find { it.id == id }
            ?: getComingSoonMovies().find { it.id == id }
    }

    suspend fun getCinemas(): List<Cinema> {
        delay(1000)
        return listOf(
            Cinema(
                id = "1",
                name = "CGV Grand Indonesia",
                address = "Jl. M.H. Thamrin No.1",
                city = "Jakarta",
                distance = 2.5f,
                logoUrl = "https://example.com/cgv.jpg"
            ),
            // Add more cinemas...
        )
    }

    suspend fun getCinemaById(id: String): Cinema? {
        delay(500)
        return getCinemas().find { it.id == id }
    }
}