package com.rizkimuhammadmukti.cinematicketapp.di

import com.rizkimuhammadmukti.cinematicketapp.data.repository.CinemaRepository
import com.rizkimuhammadmukti.cinematicketapp.data.repository.MovieRepository
import org.koin.dsl.module

val appModule = module {
    single { MovieRepository() }
    single { CinemaRepository() }
}