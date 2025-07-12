package com.rizkimuhammadmukti.cinematicketapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rizkimuhammadmukti.cinematicketapp.data.repository.*
import com.rizkimuhammadmukti.cinematicketapp.data.source.FakeDataSource
import org.koin.dsl.module

val appModule = module {
    // Firebase Services
    single { FirebaseAuth.getInstance() }
    single {
        FirebaseDatabase.getInstance("https://cinematicketapp-b47de-default-rtdb.firebaseio.com/").apply {
            setPersistenceEnabled(true)
        }
    }

    // Data Sources
    single { FakeDataSource() }

    // Repositories
    single { FirebaseAuthRepository(get()) }
    single { FirebaseUserRepository(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single { MovieRepository() }
    single { CinemaRepository() }
    single<TicketRepository> { TicketRepositoryImpl(get()) }  // Add this line
}