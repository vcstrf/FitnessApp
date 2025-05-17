package com.example.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

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
            tab.text = when(position) {
                0 -> "Моя"
                1 -> "Пользователей"
                else -> null
            }
        }.attach()
    }
}

class ViewPagerAdapter(fragment: Fragment) : androidx.viewpager2.adapter.FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}