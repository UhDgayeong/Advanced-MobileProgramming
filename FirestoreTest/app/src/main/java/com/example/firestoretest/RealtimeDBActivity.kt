package com.example.firestoretest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firestoretest.databinding.ActivityRealtimeDbactivityBinding
import com.google.firebase.ktx.Firebase

class RealtimeDBActivity : AppCompatActivity() {
    lateinit var binding: ActivityRealtimeDbactivityBinding
    //private val database = Firebase.database
    //private val itemsRef = database.getReference("items")
    private var adapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_dbactivity)
    }
}