package com.example.woloxapp.ui.Home.tablayout.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.woloxapp.ui.Home.tablayout.fragments.NewsFragment
import com.example.woloxapp.ui.Home.tablayout.fragments.ProfileFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewsFragment()
            }
            1 -> {
                ProfileFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}
