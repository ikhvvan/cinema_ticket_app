package com.rizkimuhammadmukti.cinematicketapp.data.source

import com.rizkimuhammadmukti.cinematicketapp.data.model.Cinema
import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import com.rizkimuhammadmukti.cinematicketapp.data.model.Ticket
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.random.Random

class FakeDataSource {
    // Movie functions
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
            Movie(
                id = "2",
                title = "The Dark Knight",
                genre = "Action, Crime, Drama",
                duration = "2h 32m",
                rating = 9.0f,
                posterUrl = "https://example.com/darkknight.jpg",
                synopsis = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.",
                showTimes = listOf("11:00", "14:30", "18:00", "21:30")
            )
        )
    }

    suspend fun getComingSoonMovies(): List<Movie> {
        delay(1000)
        return listOf(
            Movie(
                id = "3",
                title = "Black Widow",
                genre = "Action, Adventure, Sci-Fi",
                duration = "2h 14m",
                rating = 7.2f,
                posterUrl = "https://example.com/blackwidow.jpg",
                synopsis = "Natasha Romanoff confronts the darker parts of her ledger.",
                showTimes = emptyList()
            ),
            Movie(
                id = "4",
                title = "Dune",
                genre = "Adventure, Sci-Fi",
                duration = "2h 35m",
                rating = 8.0f,
                posterUrl = "https://example.com/dune.jpg",
                synopsis = "Feature adaptation of Frank Herbert's science fiction novel.",
                showTimes = emptyList()
            )
        )
    }

    suspend fun getMovieById(id: String): Movie? {
        delay(500)
        return getNowPlayingMovies().find { it.id == id }
            ?: getComingSoonMovies().find { it.id == id }
    }

    // Cinema functions
    suspend fun getCinemas(): List<Cinema> {
        delay(1000)
        return listOf(
            Cinema(
                id = "1",
                name = "CGV Grand Indonesia",
                address = "Jl. M.H. Thamrin No.1",
                city = "Jakarta",
                distance = 2.5f,
                logoUrl = "https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=400" // Real cinema image
            ),
            Cinema(
                id = "2",
                name = "XXI Taman Anggrek",
                address = "Jl. Letjen S. Parman No.1",
                city = "Jakarta",
                distance = 3.0f,
                logoUrl = "https://images.unsplash.com/photo-1542204165-65bf26472b9b?w=400" // Real cinema image
            )
        )
    }

    suspend fun getCinemaById(id: String): Cinema? {
        delay(500)
        return getCinemas().find { it.id == id }
    }

    // Ticket functions
    suspend fun getUserTickets(): List<Ticket> {
        delay(1000)
        return listOf(
            Ticket(
                id = "1",
                movieId = "1",
                movieTitle = "Avengers: Endgame",
                cinemaId = "1",
                cinemaName = "CGV Grand Indonesia",
                showtime = "2023-06-15T14:30:00",
                seatNumber = "A5",
                bookingDate = "2023-06-10",
                barcode = "AVG123456",
                totalPrice = 45000.0,
                posterUrl = "https://example.com/avengers.jpg"
            ),
            Ticket(
                id = "2",
                movieId = "2",
                movieTitle = "The Dark Knight",
                cinemaId = "2",
                cinemaName = "XXI Taman Anggrek",
                showtime = "2023-06-16T19:00:00",
                seatNumber = "B3",
                bookingDate = "2023-06-11",
                barcode = "TDK789012",
                totalPrice = 50000.0,
                posterUrl = "https://example.com/darkknight.jpg"
            )
        )
    }

    suspend fun getTicketById(id: String): Ticket? {
        delay(500)
        return getUserTickets().find { it.id == id }
    }

    suspend fun bookTicket(
        movieId: String,
        cinemaId: String,
        showtime: String,
        seatNumber: String
    ): Ticket {
        delay(1500)
        val movie = getMovieById(movieId) ?: throw Exception("Movie not found")
        val cinema = getCinemaById(cinemaId) ?: throw Exception("Cinema not found")

        return Ticket(
            id = (getUserTickets().size + 1).toString(),
            movieId = movieId,
            movieTitle = movie.title,
            cinemaId = cinemaId,
            cinemaName = cinema.name,
            showtime = showtime,
            seatNumber = seatNumber,
            bookingDate = LocalDate.now().toString(),
            barcode = "TKT${Random.nextInt(1000, 9999)}",
            totalPrice = 50000.0,
            posterUrl = movie.posterUrl
        )
    }
}