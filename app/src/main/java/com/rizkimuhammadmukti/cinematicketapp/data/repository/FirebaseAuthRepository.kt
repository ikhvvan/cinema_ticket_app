package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun register(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user
        } catch (e: Exception) {
            throw e
        }
    }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()
}