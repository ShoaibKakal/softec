package com.example.techevent.homescreen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.techevent.databinding.ActivityStallCompetitionBinding
import com.example.techevent.untils.PageAdapter
import com.google.android.material.tabs.TabLayout

class StallCompetitionActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    private lateinit var binding: ActivityStallCompetitionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStallCompetitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tableLayout
        viewPager = binding.mainViewPager

        tabLayout.addTab(tabLayout.newTab().setText("Stall Bids"))
        tabLayout.addTab(tabLayout.newTab().setText("Competition Bids"))


        val pageAdapter = PageAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = pageAdapter


        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }
}