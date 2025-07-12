package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.rizkimuhammadmukti.cinematicketapp.data.model.Ticket
import com.rizkimuhammadmukti.cinematicketapp.data.source.FakeDataSource
import timber.log.Timber

interface TicketRepository {
    suspend fun getUserTickets(): List<Ticket>
    suspend fun getTicketById(id: String): Ticket?
}

class TicketRepositoryImpl(
    private val fakeDataSource: FakeDataSource
) : TicketRepository {

    override suspend fun getUserTickets(): List<Ticket> {
        return try {
            fakeDataSource.getUserTickets().also {
                Timber.d("Successfully fetched ${it.size} tickets")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get user tickets")
            emptyList()
        }
    }

    override suspend fun getTicketById(id: String): Ticket? {
        return try {
            fakeDataSource.getTicketById(id)?.also {
                Timber.d("Found ticket with ID: $id")
            } ?: run {
                Timber.w("Ticket with ID $id not found")
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting ticket by ID: $id")
            null
        }
    }
}