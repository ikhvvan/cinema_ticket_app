package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.rizkimuhammadmukti.cinematicketapp.data.model.User

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    fun getCurrentUser(): User?
    fun logout()
}