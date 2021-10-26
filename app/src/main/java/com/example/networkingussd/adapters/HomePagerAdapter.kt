package com.example.networkingussd.adapters


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.networkingussd.fragments.Pager
import com.example.networkingussd.fragments.SelectedFragment

class HomePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            Pager()
        } else {
            SelectedFragment()
        }
    }
}