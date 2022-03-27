package com.example.techevent.targetscreen.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.homescreen.model.SponsorPackages
import com.example.techevent.untils.StallBidListiner


class ApplicationStatusAdapter(
    val itemList: ArrayList<ApplicationStatus>,
    val listiner: StallBidListiner
) :
    RecyclerView.Adapter<ApplicationStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_application_status, parent, false)
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
        val teamName = view.findViewById<TextView>(R.id.tv_team_name)
        val teamMembes = view.findViewById<TextView>(R.id.tv_team_members)
        val status = view.findViewById<TextView>(R.id.application_status)
        fun bindData(applicationStatus: ApplicationStatus) {

            title.text = applicationStatus.name
            teamName.text = "Team Name: ${applicationStatus.teamName}"
            teamMembes.text = "Team Members: ${applicationStatus.teamMembers}"

            if (applicationStatus.status) {
                status.text = "Confirmed"
                status.setBackgroundResource(R.drawable.status_background)

            } else {
                status.text = "Pending"
                status.setBackgroundResource(R.drawable.bid_orange_background)
                //change the background color
            }
        }
    }
}
