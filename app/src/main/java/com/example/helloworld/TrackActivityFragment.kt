package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class TrackActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_track_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityType = arguments?.getString("ACTIVITY_TYPE")

        view.findViewById<TextView>(R.id.titleText).text = activityType

        val buttonFinish = view.findViewById<ImageView>(R.id.buttonFinish)
        buttonFinish.setOnClickListener {
            val intent = Intent(requireContext(), MainNavigationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
