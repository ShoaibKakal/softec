package com.example.techevent.homescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.databinding.FragmentCompetitionBidsBinding
import com.example.techevent.homescreen.activities.DetailCompetitionActivity
import com.example.techevent.homescreen.model.Competition
import com.example.techevent.homescreen.model.CompetitionAdapter
import com.example.techevent.untils.CompetitionListiner
import com.example.techevent.untils.Constants

class CompetitionBidsFragment : Fragment(), CompetitionListiner {

    private lateinit var binding: FragmentCompetitionBidsBinding
    private lateinit var competitionAdapter: CompetitionAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompetitionBidsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewCompetitionBids)


        val itemList = arrayListOf(
            Competition(
                R.drawable.app_dev,
                "Mobile Application Development",
                50000,
                25000,
                "In the era of smartphones, mobile application development is field which attracts a lot of developers. SOFTEC being the biggest IT extravaganza of Pakistan feels the need of introducing a competition, targeted at the mobile application developers. This competition, when executed, will provide a healthy environment for the students from all over Pakistan to show off their mobile application development skills. You can show off your Android or iOS native application development skills here to a large number of Mobile Application development companies and get a chance to be hired by one of them, along with the prize money."
            ),
            Competition(
                R.drawable.artificial_intelligence,
                "Artificial Intelligence",
                50000,
                25000,
                "Artificial Intelligence Competition was named as Data Science Competition when it was introduced for the first time as a highly anticipated event during SOFTEC 2019. If you think you own a mixture of multidisciplinary skills ranging from an intersection of Mathematics, Statistics, Computer Science, Communication and Bussiness then it's the right time to show the world. This competition let you prove your skills not just to the world but most importantly to yourself."
            ),
            Competition(
                R.drawable.engineering_project,
                "Engineering Project Competition",
                80000,
                40000,
                "For too long the hardware engineering aspects of I.T. work in Pakistan had been merely an afterthought in professional competitions. With the intentions of changing this attitude, the Engineering Project Competition, a highly anticipated event was launched for the first time during SOFTEC 2007. The exceedingly ambitious hardware-based projects submitted to the software competition in recent years, as well as our institute’s steps into the hardware engineering education arena prompted us to initiate this competition exclusively for engineering projects of undergraduate students. The evaluation of entries will follow the same pattern as that of the software competition, with participants’ entries undergoing three rounds of analysis by a panel of expert judges."
            ),
            Competition(
                R.drawable.gaming_competition,
                "Gaming Competition",
                80000,
                40000,
                "The profession of gaming is rapidly gaining ground in Pakistan. No longer is the activity relegated to the fringes of the I.T. industry; it is now recognised as a lucrative career choice in its own right. The gaming competition also draws intense interest from spectators. Spectator arenas are set up to allow people to watch the progress of tournaments. The palpable excitement around these arenas attracts multitudes of curious visitors besides those coming specifically to watch, making the gaming competition one of the most eagerly followed competitions of SOFTEC."
            ),
            Competition(
                R.drawable.graphic_designing,
                "Graphic Designing",
                40000,
                20000,
                "Dreamers are mocked as impractical. The truth is that they are the most practical of us all. Can you portray your inner dreamer into a poster? If yes, then this competition is all for you. Come to SOFTEC’s Graphic Designing Competition and compete with fellow dreamers."
            ),
            Competition(
                R.drawable.ideas_xtreme,
                "Ideas Xtreme",
                80000,
                40000,
                "Ideas Xtreme is a competition in which candidates can present their idea based on a specific theme decided by our team, through presentation using any presentation software rather than a model. Given their Idea must be practical and related to science/technology and participants must be able to relate their idea to benefits for people and or the change it could bring for the people, in short application of idea must be presented after presentation of ideas."
            ),
            Competition(
                R.drawable.programming_competition,
                "Programming Competition",
                100000,
                50000,
                "The Programming Competition (formerly the Dynamic Programming Competition) is a challenging arena, attracting bright and sharp minds from all over Asia. Programming under timed conditions tests the skills and grit of the participants involved to the limit. SOFTEC 2021 will provide local participants the opportunity to match their skills with a much wider field of competitors than otherwise available. The international event will gear the participants to perform under pressure and give them an insight as to where they stand in comparison with their counterparts from countries throughout the world. The competition is structured to test participants in the arena of speed programming – coming up with a solution to a complex question in limited time."
            ),
            Competition(
                R.drawable.roborumble_competition,
                "Robo Rumble Competition",
                40000,
                20000,
                "Get ready to witness the rise of machines and be enthralled by the steel against steel as SOFTEC introduces the cyber matchups which will see robots take on robots in Robo Rumble. This competition provides an innovative opportunity for tech and hardware lovers to give identity and life to their creations and translates a passion of morphing into reality. We will provide the Coliseum, we will provide the cheering-mad crowd and the tech wizards will provide the machines."
            ),
            Competition(
                R.drawable.software_project,
                "Software Project Competition",
                100000,
                50000,
                "The Software Competition was the very first event included in the roster of SOFTEC, in its advent in 1995. It has become the core around which the entire SOFTEC event is now arranged. This competition brings together students from across the world to display their software projects for formal judging by a team of experts, providing participants with valuable feedback and experience of real-world evaluation benchmarks. Not only is this a source of erudition for the participants, but visitors to the exhibition are more often than not students seeking projects with the potential for future development, or even I.T. industry professionals looking for promising talent among the participants."
            ),
            Competition(
                R.drawable.web_dec_competition,
                "Web Development Competition",
                80000,
                40000,
                "Different teams from renowned institutes participate to test their web development skills against a broader and tougher spectrum of students and get to know the most recent developments in the field by the experts. Competing with the tough and challenging opponents will not only provide you a fruitful experience but will also help you to analyse in depth, your weaknesses and strengths, helping you out later in your career. So if you don’t want to miss the thrill, sign up and experience the stimulating atmosphere."
            )

        )


        competitionAdapter = CompetitionAdapter(itemList, this)

        recyclerView = binding.recyclerViewCompetitionBids
        recyclerView.adapter = competitionAdapter
    }


    override fun onItemClicked(competition: Competition) {
        val intent = Intent(requireContext(), DetailCompetitionActivity::class.java)
        intent.putExtra(Constants.KEY_COMPETITION, competition)
        startActivity(intent)
    }
}