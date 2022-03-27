package com.example.techevent.homescreen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.techevent.databinding.ActivityDetailCompetitionBinding
import com.example.techevent.homescreen.model.Competition
import com.example.techevent.untils.Constants

class DetailCompetitionActivity : AppCompatActivity() {
    private lateinit var competition: Competition
    private lateinit var binding: ActivityDetailCompetitionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCompetitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        competition = intent.getSerializableExtra(Constants.KEY_COMPETITION) as Competition

        binding.tvName.text = competition.name
        binding.description.text = competition.description
        binding.tvWinnerPrize.text = "${competition.winnerPrize} PKR"
        binding.tvRunnerUpPrize.text = "${competition.runnerUpPrize} PKR"
        binding.imageUniversityLogo.setImageResource(competition.image)

        binding.imageBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonRegiterNow.setOnClickListener {
            val intent = Intent(this, CompetitionRegisterActivity::class.java)
            intent.putExtra(Constants.KEY_NAME, competition.name)
            startActivity(intent)
        }


    }

}