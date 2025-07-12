package com.rizkimuhammadmukti.cinematicketapp.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.rizkimuhammadmukti.cinematicketapp.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    private val usersRef by lazy { firebaseDatabase.getReference("users") }

    suspend fun saveUser(user: User) {
        try {
            usersRef.child(user.id).setValue(user).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUser(userId: String): User? {
        return try {
            val snapshot = usersRef.child(userId).get().await()
            snapshot.getValue(User::class.java)
        } catch (e: Exception) {
            throw e
        }
    }
}