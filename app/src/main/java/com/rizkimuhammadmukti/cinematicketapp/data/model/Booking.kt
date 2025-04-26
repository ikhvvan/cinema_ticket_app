package com.rizkimuhammadmukti.cinematicketapp.data.model

data class Booking(
    val id: String,
    val movieId: String,
    val cinemaId: String,
    val userId: String,
    val showTime: String,
    val seats: List<String>,
    val totalPrice: Double,
    val bookingDate: String,
    val status: String
)