package com.example.firebase_2021_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase_2021_2.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Firebase.auth.signInWithEmailAndPassword("a@a.com", "123456")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("########## Login Success")
                    println(Firebase.auth.currentUser?.uid)
                } else {
                    it.exception?.message
                    println("########## Login Failed ${it.exception?.message}")
                }
            }

        Firebase.auth.createUserWithEmailAndPassword("c@b.com", "123456")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    println("########## Sign-up Success")
                    println(Firebase.auth.currentUser?.uid)
                } else {
                    it.exception?.message
                    println("########## Sign-up Failed ${it.exception?.message}")
                }
            }*/

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        binding.textView.text = Firebase.auth.currentUser?.uid ?: "No User"

        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
}