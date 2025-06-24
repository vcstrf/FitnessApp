package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class TrackActivityFragment : Fragment() {
    private val repository by lazy { ActivitiesRepository.getInstance() }

    private var generatedDuration: String = ""
    private var generatedDistance: String = ""
    private var generatedStartTime: String = ""
    private var generatedFinishTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_track_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = arguments?.getString("ACTIVITY_TYPE") ?: "Велосипед"
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date

        val durationMinutes = Random.nextInt(10, 90)
        val distanceKm = Random.nextDouble(0.5, 15.0)

        val startHour = Random.nextInt(6, 20)
        val startMinute = Random.nextInt(0, 60)
        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        calendar.set(Calendar.MINUTE, startMinute)
        val startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        calendar.add(Calendar.MINUTE, durationMinutes)
        val finishTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        generatedDuration = "$durationMinutes мин"
        generatedDistance = String.format("%.1f км", distanceKm)
        generatedStartTime = startTime
        generatedFinishTime = finishTime

        view.findViewById<TextView>(R.id.titleText).text = type
        view.findViewById<TextView>(R.id.distanceText).text = generatedDistance
        view.findViewById<TextView>(R.id.timerText).text = generatedDuration

        view.findViewById<ImageView>(R.id.buttonFinish).setOnClickListener {
            saveNewActivity(type)
            val intent = Intent(requireContext(), MainNavigationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        val buttonBack = view.findViewById<ImageView>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val mainFragment = StartActivityFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, mainFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun saveNewActivity(type: String) {
        lifecycleScope.launch {
            val newActivity = Activity(
                id = 0,
                user = "me",
                duration = generatedDuration,
                type = type,
                distance = generatedDistance,
                date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()),
                startTime = generatedStartTime,
                finishTime = generatedFinishTime,
                timeAgo = "Сейчас",
                isFromOtherUser = false
            )
            repository.insertActivity(newActivity)
        }
    }
}

