package com.example.techevent.homescreen.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.untils.StallBidListiner


class SponsorPackageAdapter(
    val itemList: ArrayList<SponsorPackages>,
    val listiner: StallBidListiner
) :
    RecyclerView.Adapter<SponsorPackageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_package_card, parent, false)
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

        val title = view.findViewById<TextView>(R.id.package_name)
        val packageLeft = view.findViewById<TextView>(R.id.package_left)
        val status = view.findViewById<TextView>(R.id.status)
        fun bindData(sponsorPackage: SponsorPackages) {

            title.text = sponsorPackage.title
            packageLeft.text = "Packages Left: ${sponsorPackage.packagesLeft}"
            status.text = "Status: ${sponsorPackage.status}"

            if (sponsorPackage.status) {
                status.text = "Status: Open"
                status.setBackgroundResource(R.drawable.status_background)

            } else {
                status.text = "Status: Closed"
                status.setBackgroundResource(R.drawable.bid_red_background)
                //change the background color
            }
        }
    }
}
