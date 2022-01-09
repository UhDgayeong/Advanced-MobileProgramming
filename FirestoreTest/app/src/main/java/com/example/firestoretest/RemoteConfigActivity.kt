package com.example.firestoretest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firestoretest.databinding.ActivityRemoteConfigBinding
import com.google.firebase.ktx.Firebase

class RemoteConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRemoteConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}