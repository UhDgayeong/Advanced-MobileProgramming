package com.example.myfirebasestorage

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myfirebasestorage.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //throw RuntimeException("###################")

        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setDefaultsAsync(R.xml.rc_defaults)
        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(settings)

        getSeasonImage()

        binding.buttonRefresh.setOnClickListener {
            getSeasonImage()
        }

    }

    private fun getSeasonImage() {
        lateinit var season : String
        lateinit var ref : StorageReference
        val remoteConfig = Firebase.remoteConfig
        val rootRef = Firebase.storage.reference

        remoteConfig.fetchAndActivate().addOnSuccessListener {
            season = remoteConfig.getString("season")
            //println("###################### $season")
            if (season == "spring")
                ref = rootRef.child("spring.jpg")
            else if (season == "summer")
                ref = rootRef.child("summer.jpg")
            else if (season == "fall")
                ref = rootRef.child("fall.jpg")
            else if (season == "winter")
                ref = rootRef.child("winter.jpg")

            ref.getBytes(Long.MAX_VALUE).addOnCompleteListener {
                if (it.isSuccessful) {
                    val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
                    val imgView = findViewById<ImageView>(R.id.imageView)
                    imgView.setImageBitmap(bmp)
                }
            }
        }

    }
}