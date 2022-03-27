package com.example.techevent.homescreen.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.untils.CompetitionListiner
import com.example.techevent.untils.StallBidListiner


class StallBidsAdapter(val itemList: ArrayList<StallBid>, val listiner: StallBidListiner) :
    RecyclerView.Adapter<StallBidsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bid_card, parent, false)
        return ViewHolder(view, listiner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(val view: View, val listiner: StallBidListiner) :
        RecyclerView.ViewHolder(view) {

        val stallTitle = view.findViewById<TextView>(R.id.stall_type)
        val bidStaus = view.findViewById<TextView>(R.id.bid_status)
        val bidRange = view.findViewById<TextView>(R.id.bid_range)
        val bidDescription = view.findViewById<TextView>(R.id.bid_description)
        val bidders = view.findViewById<TextView>(R.id.no_of_bidders)

        fun bindData(stallBid: StallBid) {

            stallTitle.text = stallBid.stallTitle
            if (stallBid.bidStatus) {
                bidStaus.text = "Status: Open"
                bidStaus.setBackgroundResource(R.drawable.status_background)

            } else {
                bidStaus.text = "Status: Closed"
                bidStaus.setBackgroundResource(R.drawable.bid_red_background)
                //change the background color
            }

            bidRange.text = "Bid Range: ${stallBid.minBid} - ${stallBid.maxBid}"
            bidDescription.text = "- ${stallBid.desc_1}\n- ${stallBid.desc_2}\n- ${stallBid.desc_3}"

            bidders.text = "No. of Bidders: ${stallBid.noOfBidders}"


            view.rootView.setOnClickListener {
                listiner.onItemClicked(stallBid)
            }
        }
    }
}
