package com.example.helloworld

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
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

class ActivitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = App.INSTANCE.db.activityDao()
    private val repository = ActivitiesRepository(App.INSTANCE.db.activityDao())

    val myActivities = dao.getMyActivities().asLiveData()
    val usersActivities = MutableLiveData<List<Activity>>().apply {
        value = repository.getUsersActivities()
    }
}

class MyActivitiesFragment : Fragment() {
    private lateinit var adapter: ActivityAdapter
    private lateinit var viewModel: ActivitiesViewModel
    private lateinit var textEmpty1: TextView
    private lateinit var textEmpty2: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_activity_mine, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        textEmpty1 = view.findViewById(R.id.textMain)
        textEmpty2 = view.findViewById(R.id.textSecondary)

        adapter = ActivityAdapter { activity ->
            val intent = Intent(requireContext(), ActivityDetailsActivity::class.java).apply {
                putExtra("ACTIVITY", activity)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ActivitiesViewModel::class.java]

        viewModel.myActivities.observe(viewLifecycleOwner) { activities ->
            adapter.submitActivities(activities)
            if (adapter.itemCount == 0) {
                textEmpty1.visibility = View.VISIBLE
                textEmpty2.visibility = View.VISIBLE
            } else {
                textEmpty1.visibility = View.GONE
                textEmpty2.visibility = View.GONE
            }
        }
    }
}


class UsersActivitiesFragment : Fragment() {
    private lateinit var adapter: ActivityAdapter
    private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_activity_users, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        adapter = ActivityAdapter { activity ->
            val intent = Intent(requireContext(), ActivityDetailsActivity::class.java).apply {
                putExtra("ACTIVITY", activity)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ActivitiesViewModel::class.java]

        viewModel.usersActivities.observe(viewLifecycleOwner) { activities ->
            adapter.submitActivities(activities)
        }
    }
}


