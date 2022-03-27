package com.example.techevent.untils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.techevent.homescreen.CompetitionBidsFragment
import com.example.techevent.homescreen.StallFragment


class PageAdapter(fm: FragmentManager, private val totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return StallFragment()
        } else if (position == 1) return CompetitionBidsFragment()
        return StallFragment()
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
