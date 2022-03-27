package com.example.techevent.untils

import com.example.techevent.homescreen.model.Competition
import com.example.techevent.homescreen.model.StallBid

interface StallBidListiner {

    fun onItemClicked(stallBid: StallBid)

}