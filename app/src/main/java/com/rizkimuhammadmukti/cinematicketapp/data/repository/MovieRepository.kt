package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.rizkimuhammadmukti.cinematicketapp.data.model.Movie
import com.rizkimuhammadmukti.cinematicketapp.data.source.FakeDataSource

class MovieRepository {
    private val fakeDataSource = FakeDataSource()

    suspend fun getNowPlayingMovies(): List<Movie> {
        return fakeDataSource.getNowPlayingMovies()
    }

    suspend fun getComingSoonMovies(): List<Movie> {
        return fakeDataSource.getComingSoonMovies()
    }

    suspend fun getMovieById(id: String): Movie? {
        return fakeDataSource.getMovieById(id)
    }
}