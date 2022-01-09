package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createNotificationChannel()

        binding.button.setOnClickListener {
            count++
            showNotification()
        }
    }

    private val channelID = "normal"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelID, "default channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "description text of this channel."
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification() {
        val builder = with (NotificationCompat.Builder(this, channelID)) {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("버튼 누른 횟수")
            setContentText("$count 회")
        }
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }
}