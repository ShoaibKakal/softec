package com.example.techevent.homescreen.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.untils.CompetitionListiner

class CompetitionAdapter(val itemList: ArrayList<Competition>, val listiner: CompetitionListiner) :
    RecyclerView.Adapter<CompetitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_competition, parent, false)
        return ViewHolder(view, listiner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(val view: View, val listiner: CompetitionListiner) :
        RecyclerView.ViewHolder(view) {

        val competition_name = view.findViewById<TextView>(R.id.tv_competition_name)
        val logo = view.findViewById<ImageView>(R.id.image_logo)
        val winnerPrize = view.findViewById<TextView>(R.id.tv_winner_prize)
        val description = view.findViewById<TextView>(R.id.tv_description)

        fun bindData(competition: Competition) {
            competition_name.text = competition.name
            logo.setImageResource(competition.image)
            winnerPrize.text = "Winner Prize: ${competition.winnerPrize}"
            description.text = competition.description


            view.rootView.setOnClickListener {
                listiner.onItemClicked(competition)
            }
        }
    }

}