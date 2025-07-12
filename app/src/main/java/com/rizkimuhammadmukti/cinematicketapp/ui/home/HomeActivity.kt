package com.rizkimuhammadmukti.cinematicketapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizkimuhammadmukti.cinematicketapp.R
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityHomeBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.auth.LoginActivity
import com.rizkimuhammadmukti.cinematicketapp.ui.home.fragment.CinemasFragment
import com.rizkimuhammadmukti.cinematicketapp.ui.home.movies.MoviesFragment
import com.rizkimuhammadmukti.cinematicketapp.ui.home.fragment.TicketsFragment
import com.rizkimuhammadmukti.cinematicketapp.utils.SharedPrefs

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPrefs: SharedPrefs
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar as ActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        sharedPrefs = SharedPrefs(this)
        setupBottomNavigation()

        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MoviesFragment())
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    loadFragment(MoviesFragment())
                    true
                }
                R.id.nav_cinemas -> {
                    loadFragment(CinemasFragment())
                    true
                }
                R.id.nav_tickets -> {
                    loadFragment(TicketsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Rest of the code remains the same (onCreateOptionsMenu, onOptionsItemSelected, etc.)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {
        sharedPrefs.clearUserData()
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }
}