package com.rizkimuhammadmukti.cinematicketapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityLoginBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.HomeActivity
import com.rizkimuhammadmukti.cinematicketapp.utils.SharedPrefs

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val prefs by lazy { SharedPrefs(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (prefs.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(email, password)) {
                prefs.setLoggedIn(true)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email cannot be empty"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password cannot be empty"
            return false
        }

        // Check if credentials match registered user
        val registeredEmail = prefs.getUserEmail()
        val registeredPassword = prefs.getUserPassword()

        if (email != registeredEmail || password != registeredPassword) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}