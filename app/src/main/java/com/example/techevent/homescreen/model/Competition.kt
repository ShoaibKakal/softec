package com.example.techevent.homescreen.model

import java.io.Serializable

data class Competition(var image:Int, var name:String, var winnerPrize:Int,var runnerUpPrize:Int, var description:String) :
    Serializable