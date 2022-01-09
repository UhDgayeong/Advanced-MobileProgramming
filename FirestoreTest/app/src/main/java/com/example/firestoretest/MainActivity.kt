package com.example.firestoretest

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.example.firestoretest.FirestoreActivity.Companion.TAG
import com.example.firestoretest.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        binding.textUID.text = Firebase.auth.currentUser?.uid ?: "No User"

        binding.buttonSignout.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            binding.textFCMToken.text = if (it.isSuccessful) it.result else "Token Error!"

            // copy FCM token to clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("FCM Token", binding.textFCMToken.text)
            clipboard.setPrimaryClip(clip)

            // write to logcat
            //Log.d(MyFirebaseMessagingService.TAG, "FCM token: ${binding.textFCMToken.text}")
        }

        binding.textFCMToken.setTextIsSelectable(true);
        binding.textMessage.visibility = View.INVISIBLE

        var message = intent.getStringExtra("message").toString()
        if (message != "null") {
            binding.textMessage.visibility = View.VISIBLE
            binding.textMessage.text = message
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.storage -> startActivity(
                Intent(this, StorageActivity::class.java))
            R.id.remote_config -> startActivity(
                Intent(this, RemoteConfigActivity::class.java))
            R.id.firestore -> startActivity(
                Intent(this, FirestoreActivity::class.java))
            R.id.realtime_db -> startActivity(
                Intent(this, RealtimeDBActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}