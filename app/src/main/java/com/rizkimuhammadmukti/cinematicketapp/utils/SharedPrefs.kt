package com.rizkimuhammadmukti.cinematicketapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("CinemaTicketPrefs", Context.MODE_PRIVATE)

    // Existing method for login status
    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    // New methods for user data
    fun setUserData(name: String, email: String, password: String) {
        prefs.edit().apply {
            putString("USER_NAME", name)
            putString("USER_EMAIL", email)
            putString("USER_PASSWORD", password)
            apply()
        }
    }

    fun getUserName(): String? {
        return prefs.getString("USER_NAME", null)
    }

    fun getUserEmail(): String? {
        return prefs.getString("USER_EMAIL", null)
    }

    fun getUserPassword(): String? {
        return prefs.getString("USER_PASSWORD", null)
    }

    // Optional: Method to clear user data on logout
    fun clearUserData() {
        prefs.edit().apply {
            remove("USER_NAME")
            remove("USER_EMAIL")
            remove("USER_PASSWORD")
            putBoolean("is_logged_in", false)
            apply()
        }
    }
}