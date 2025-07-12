package com.rizkimuhammadmukti.cinematicketapp.ui.auth

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.ktx.Firebase
import com.rizkimuhammadmukti.cinematicketapp.databinding.ActivityLoginBinding
import com.rizkimuhammadmukti.cinematicketapp.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Setup auth state listener
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                navigateToHome()
            }
        }

        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            showToast("Forgot password feature coming soon")
        }
    }

    private fun loginUser(email: String, password: String) {
        binding.btnLogin.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    showToast("Login successful")
                    navigateToHome()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    handleLoginError(e)
                }
            }
        }
    }

    private fun handleLoginError(e: Exception) {
        val errorMessage = when {
            e is FirebaseAuthInvalidUserException -> {
                Log.e("LoginError", "Email not registered", e)
                "Email not registered. Please register first."
            }
            e is FirebaseAuthInvalidCredentialsException ||
                    e.message?.contains("INVALID_LOGIN_CREDENTIALS") == true -> {
                Log.e("LoginError", "Invalid email or password", e)
                "Invalid email or password. Please try again."
            }
            e is FirebaseTooManyRequestsException -> {
                Log.e("LoginError", "Too many requests", e)
                "Too many attempts. Please try again later."
            }
            else -> {
                Log.e("LoginError", "Login failed: ${e.message}", e)
                "Login failed. Please check your connection and try again."
            }
        }
        showToast(errorMessage)
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email cannot be empty"
            binding.tilEmail.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Invalid email format"
            binding.tilEmail.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password cannot be empty"
            binding.tilPassword.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            binding.tilPassword.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        return isValid
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}