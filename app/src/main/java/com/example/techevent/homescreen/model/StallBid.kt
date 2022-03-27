package com.example.techevent.homescreen.model

import java.io.Serializable

data class StallBid(
    var stallTitle: String,
    val noOfBidders: Double,
    val bidStatus: Boolean,
    val maxBid: Double,
    val minBid: Double,
    val desc_1: String,
    val desc_2: String,
    val desc_3: String,
    val bidID:String
): Serializable