package com.example.helloworld
import android.os.Bundle
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
        val startTimeText = findViewById<TextView>(R.id.startTimeText)
        val finishTimeText = findViewById<TextView>(R.id.finishTimeText)
        val usernameText = findViewById<TextView>(R.id.usernameText)

        val activity = intent.getSerializableExtra("ACTIVITY") as? Activity

        activity?.let {
            titleText.text = it.type
            distanceText.text = it.distance
            timeAgoText.text = it.timeAgo
            durationText.text = it.duration
            startTimeText.text = if (activity.startTime.isBlank()) "00:25" else activity.startTime
            finishTimeText.text = if (activity.finishTime.isBlank()) "|  00:50" else "|  " + it.finishTime

            if (it.isFromOtherUser == true && !it.user.isNullOrEmpty()) {
                usernameText.visibility = View.VISIBLE
                usernameText.text = it.user
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