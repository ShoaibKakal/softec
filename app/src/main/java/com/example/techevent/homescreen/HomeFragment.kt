package com.example.techevent.homescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.techevent.R
import com.example.techevent.databinding.FragmentHomeBinding
import com.example.techevent.homescreen.activities.DetailCompetitionActivity
import com.example.techevent.homescreen.activities.SponsorshipPackageActivity
import com.example.techevent.homescreen.activities.StallCompetitionActivity
import com.example.techevent.homescreen.model.Competition
import com.example.techevent.homescreen.model.CompetitionAdapter
import com.example.techevent.homescreen.model.SlideItem
import com.example.techevent.untils.CompetitionListiner
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.example.techevent.untils.SliderAdapter
import kotlin.math.abs


class HomeFragment : Fragment(), CompetitionListiner {

    private val sliderHandler = Handler()
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmentHomeBinding
    private lateinit var competitionAdapter: CompetitionAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var preferenceManager: PreferenceManager
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSlider()

        recyclerView = binding.recyclerViewCompetition
        binding.recyclerViewCompetition.visibility = View.VISIBLE

        if (preferenceManager.getString(Constants.KEY_USER_TYPE) == Constants.KEY_PARTICIPANT) {
            binding.recyclerViewCompetition.visibility = View.VISIBLE
            binding.cardViewSponsorshipPackages.visibility = View.GONE
            binding.cardViewBids.visibility = View.GONE
            binding.cardViewBids.visibility = View.GONE
        } else if (preferenceManager.getString(Constants.KEY_USER_TYPE) == Constants.KEY_SPONSOR) {
            binding.recyclerViewCompetition.visibility = View.GONE
            binding.cardViewSponsorshipPackages.visibility = View.VISIBLE
            binding.cardViewBids.visibility = View.VISIBLE
            binding.cardviewMeeting.visibility = View.VISIBLE
            binding.tvCompetition.text = "Popular Activities"
        } else {
            binding.cardViewSponsorshipPackages.visibility = View.GONE
            binding.cardViewBids.visibility = View.GONE
            binding.cardViewBids.visibility = View.GONE
            binding.recyclerViewCompetition.visibility = View.GONE
            binding.tvCompetition.text = "Event Managers are not entertained. Please log in as a Participant or Sponsor to see the Activity"

        }

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
        recyclerView.adapter = competitionAdapter


        binding.cardViewBids.setOnClickListener {
            val intent = Intent(requireContext(), StallCompetitionActivity::class.java)
            startActivity(intent)
        }

        binding.cardViewSponsorshipPackages.setOnClickListener {
            startActivity(Intent(requireContext(), SponsorshipPackageActivity::class.java))
        }

        binding.cardviewMeeting.setOnClickListener{
            val uri = Uri.parse("https://meet.google.com/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }


    private fun imageSlider() {
        viewPager2 = binding.viewpager2ImageSlider

        val sliderItems = ArrayList<SlideItem>()
        sliderItems.add(SlideItem(R.drawable.slider_1))
        sliderItems.add(SlideItem(R.drawable.slider_2))
        sliderItems.add(SlideItem(R.drawable.slider_3))
        sliderItems.add(SlideItem(R.drawable.slider_4))
        sliderItems.add(SlideItem(R.drawable.slider_5))

        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        })

        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 2000) // slider duration 3 Seconds
            }
        })
    }

    private val sliderRunnable = Runnable {

        kotlin.run {
            viewPager2.setCurrentItem(viewPager2.currentItem + 1)
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onItemClicked(competition: Competition) {
        val intent = Intent(requireContext(), DetailCompetitionActivity::class.java)
        intent.putExtra(Constants.KEY_COMPETITION, competition)
        startActivity(intent)
    }


}