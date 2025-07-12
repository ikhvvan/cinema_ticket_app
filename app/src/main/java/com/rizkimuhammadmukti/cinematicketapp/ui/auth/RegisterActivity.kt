package com.rizkimuhammadmukti.cinematicketapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityRegisterBinding
import com.rizkimuhammadmukti.cinematicketapp.data.repository.AuthRepository
import com.rizkimuhammadmukti.cinematicketapp.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInput(name, email, password, confirmPassword)) {
                registerUser(name, email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        binding.btnRegister.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.register(name, email, password)
            withContext(Dispatchers.Main) {
                binding.btnRegister.isEnabled = true
                binding.progressBar.visibility = View.GONE

                when {
                    result.isSuccess -> {
                        showToast("Registration successful!")
                        navigateToHome()
                    }
                    result.isFailure -> {
                        val errorMessage = result.exceptionOrNull()?.message ?: "Registration failed"
                        showToast(errorMessage)
                    }
                }
            }
        }
    }

    private fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.etName.error = "Name cannot be empty"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email cannot be empty"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password cannot be empty"
            isValid = false
        } else if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Please confirm your password"
            isValid = false
        } else if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords don't match"
            isValid = false
        }

        return isValid
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}