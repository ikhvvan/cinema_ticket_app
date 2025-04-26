package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.rizkimuhammadmukti.cinematicketapp.data.model.Cinema
import com.rizkimuhammadmukti.cinematicketapp.data.source.FakeDataSource

class CinemaRepository {
    private val fakeDataSource = FakeDataSource()

    suspend fun getCinemas(): List<Cinema> {
        return fakeDataSource.getCinemas()
    }

    suspend fun getCinemaById(id: String): Cinema? {
        return fakeDataSource.getCinemaById(id)
    }
}