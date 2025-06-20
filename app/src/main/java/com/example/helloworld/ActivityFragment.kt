package com.example.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager

class ActivityFragment : Fragment() {

    companion object {
        const val TAG = "ActivityFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.view_pager)

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Моя"
                1 -> "Пользователей"
                else -> null
            }
        }.attach()

        val buttonNewActivity = view.findViewById<ImageView>(R.id.buttonStart)
        buttonNewActivity.setOnClickListener {
            val intent = Intent(requireContext(), NewActivity::class.java)
            startActivity(intent)
        }
    }

}

class ViewPagerAdapter(fragment: Fragment) : androidx.viewpager2.adapter.FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MyActivitiesFragment()
            1 -> UsersActivitiesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}

class MyActivitiesFragment : Fragment() {
    private lateinit var adapter: ActivityAdapter
    private val activitiesRepository = ActivitiesRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_activities_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        adapter = ActivityAdapter(emptyList()) { activity ->
            val intent = Intent(requireContext(), ActivityDetailsActivity::class.java).apply {
                putExtra("ACTIVITY_MAIN", activity)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadMyActivities()

        return view
    }

    private fun loadMyActivities() {
        val items = activitiesRepository.getActivities(Type.MY_ACTIVITIES)
        adapter.submitList(items)
    }
}

class UsersActivitiesFragment : Fragment() {
    private lateinit var adapter: ActivityAdapter
    private val activitiesRepository = ActivitiesRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_activities_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        adapter = ActivityAdapter(emptyList()) { activity ->
            val intent = Intent(requireContext(), ActivityDetailsActivity::class.java).apply {
                putExtra("ACTIVITY_MAIN", activity)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadUsersActivities()

        return view
    }

    private fun loadUsersActivities() {
        val items = activitiesRepository.getActivities(Type.USERS_ACTIVITIES)
        adapter.submitList(items)
    }
}

