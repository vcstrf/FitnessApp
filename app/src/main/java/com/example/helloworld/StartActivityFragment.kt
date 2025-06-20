package com.example.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StartActivityFragment : Fragment() {

    private lateinit var typeAdapter: TypeAdapter
    private lateinit var currentActivityType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_start_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)

        val typeItems = listOf(
            TypeItem("Велосипед", true),
            TypeItem("Бег", false),
            TypeItem("Шаг", false)
        )

        currentActivityType = typeItems.first().name

        typeAdapter = TypeAdapter(typeItems) { selectedItem ->
            currentActivityType = selectedItem.name
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = typeAdapter

        val buttonStart = view.findViewById<Button>(R.id.buttonStart)
        buttonStart.setOnClickListener {
            val bundle = Bundle().apply {
                putString("ACTIVITY_TYPE", currentActivityType)
            }

            val trackFragment = TrackActivityFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, trackFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
