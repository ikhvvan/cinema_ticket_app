package com.rizkimuhammadmukti.cinematicketapp.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rizkimuhammadmukti.cinematicketapp.ui.home.movies.MoviesFragment
import com.rizkimuhammadmukti.cinematicketapp.ui.home.movies.CinemasFragment

class HomePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            1 -> CinemasFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}