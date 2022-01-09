package com.example.myfirebasestorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myfirebasestorage.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            var userEmail = binding.username.text.toString()
            var password = binding.password.text.toString()
            doLogin(userEmail, password)
            println("login....")
        }
        binding.signup.setOnClickListener {
            var userEmail = binding.username.text.toString()
            var password = binding.password.text.toString()
            doSignUp(userEmail, password)
            println("sign up ....")
        }
    }

    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun doSignUp(userEmail: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    println("########## Sign-up Success")
                    println(Firebase.auth.currentUser?.uid)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    it.exception?.message
                    println("########## Sign-up Failed ${it.exception?.message}")
                }
            }
    }

}