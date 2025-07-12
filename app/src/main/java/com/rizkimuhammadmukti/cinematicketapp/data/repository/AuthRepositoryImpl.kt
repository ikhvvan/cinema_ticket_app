package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.rizkimuhammadmukti.cinematicketapp.data.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseUserRepository: FirebaseUserRepository
) : AuthRepository {

    override suspend fun register(name: String, email: String, password: String): Result<User> {
        return try {
            // 1. Register auth
            val firebaseUser = firebaseAuthRepository.register(email, password)
                ?: return Result.failure(Exception("Registration failed"))

            // 2. Save user data
            val user = User(
                id = firebaseUser.uid,
                name = name,
                email = email,
                phone = "",
                password = "" // Jangan simpan password plain text di database
            )
            firebaseUserRepository.saveUser(user)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // 1. Login auth
            val firebaseUser = firebaseAuthRepository.login(email, password)
                ?: return Result.failure(Exception("Login failed"))

            // 2. Get user data
            val user = firebaseUserRepository.getUser(firebaseUser.uid)
                ?: return Result.failure(Exception("User data not found"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuthRepository.getCurrentUser()
        return firebaseUser?.let {
            User(
                id = it.uid,
                name = it.displayName ?: "",
                email = it.email ?: "",
                phone = it.phoneNumber ?: "",
                password = ""
            )
        }
    }

    override fun logout() {
        firebaseAuthRepository.logout()
    }
}