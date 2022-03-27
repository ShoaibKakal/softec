package com.example.techevent.homescreen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.databinding.ActivitySponsorshipPackageBinding
import com.example.techevent.homescreen.model.SponsorPackageAdapter
import com.example.techevent.homescreen.model.SponsorPackages
import com.example.techevent.homescreen.model.StallBid
import com.example.techevent.untils.StallBidListiner

class SponsorshipPackageActivity : AppCompatActivity(), StallBidListiner {
    private lateinit var binding: ActivitySponsorshipPackageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var sponsorPackageAdapter: SponsorPackageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySponsorshipPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sponsorPackages = arrayListOf(
            SponsorPackages("Silver Package", "001", 5.0, true),
            SponsorPackages("Diamond Package", "002", 15.0, false),
            SponsorPackages("Platinium Package", "003", 20.0, true)
        )
        sponsorPackageAdapter = SponsorPackageAdapter(sponsorPackages, this)
        recyclerView = binding.recyclerViewPackages
        recyclerView.adapter = sponsorPackageAdapter

    }

    override fun onItemClicked(stallBid: StallBid) {
    }
}