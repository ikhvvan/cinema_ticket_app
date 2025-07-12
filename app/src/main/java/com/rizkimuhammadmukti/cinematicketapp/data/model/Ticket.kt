package com.rizkimuhammadmukti.cinematicketapp.data.model

data class Ticket(
    val id: String,
    val movieId: String,
    val movieTitle: String,
    val cinemaId: String,
    val cinemaName: String,
    val showtime: String, // ISO format: "yyyy-MM-dd'T'HH:mm:ss"
    val seatNumber: String,
    val bookingDate: String, // ISO format: "yyyy-MM-dd"
    val barcode: String,
    val totalPrice: Double,
    val posterUrl: String
)