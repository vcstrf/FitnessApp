package com.example.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNavigationActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.activity_tab -> switchToFragment(ActivityFragment.TAG, ::ActivityFragment)
                R.id.profile_tab -> switchToFragment(ProfileFragment.TAG, ::ProfileFragment)
                else -> false
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ActivityFragment(), ActivityFragment.TAG)
                .commit()
        }
    }

    private fun switchToFragment(tag: String, fragmentFactory: () -> Fragment): Boolean {
        val currentFragment = supportFragmentManager.fragments.find { it.isVisible }

        val targetFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.beginTransaction().apply {
            if (currentFragment != null && currentFragment != targetFragment) {
                hide(currentFragment)
            }

            if (targetFragment != null) {
                show(targetFragment)
            } else {
                add(R.id.fragment_container, fragmentFactory(), tag)
            }

            addToBackStack(tag)
        }.commit()

        return true
    }
}
