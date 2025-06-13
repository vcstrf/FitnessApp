package com.example.helloworld
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ActivityDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val titleText = findViewById<TextView>(R.id.titleText)
        val distanceText = findViewById<TextView>(R.id.distanceText)
        val timeAgoText = findViewById<TextView>(R.id.timeAgoText)
        val durationText = findViewById<TextView>(R.id.durationText)
        val timeRangeText = findViewById<TextView>(R.id.timeRangeText)
        val usernameText = findViewById<TextView>(R.id.usernameText)

        val activity = intent.getParcelableExtra<ActivityItem.ActivityMain>("ACTIVITY_MAIN")

        activity?.let {
            titleText.text = it.type
            distanceText.text = it.distance
            timeAgoText.text = it.timeAgo
            durationText.text = it.duration
            timeRangeText.text = it.timeRange ?: "Старт 14:49  |  Финиш 16:31"

            if (it.isFromOtherUser && !it.username.isNullOrEmpty()) {
                usernameText.visibility = View.VISIBLE
                usernameText.text = it.username
            } else {
                usernameText.visibility = View.GONE
            }
        }


        val buttonBack = findViewById<ImageView>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}