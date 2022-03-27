package com.example.techevent.targetscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.R
import com.example.techevent.authentication.TAG
import com.example.techevent.databinding.FragmentTargetsBinding
import com.example.techevent.homescreen.activities.DetailStallActivity
import com.example.techevent.homescreen.model.StallBid
import com.example.techevent.homescreen.model.StallBidsAdapter
import com.example.techevent.targetscreen.model.ApplicationStatus
import com.example.techevent.targetscreen.model.ApplicationStatusAdapter
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.example.techevent.untils.StallBidListiner
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class TargetsFragment : Fragment(), StallBidListiner {

    private lateinit var binding: FragmentTargetsBinding
    private lateinit var applicationStatusAdapter: ApplicationStatusAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var stallBidsAdapter: StallBidsAdapter

    private lateinit var targetRecyclerView: RecyclerView
    private val targetBidsList = ArrayList<StallBid>()
    val database = FirebaseFirestore.getInstance()

    private var applicationsList = ArrayList<ApplicationStatus>()
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
        binding = FragmentTargetsBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerViewApplications
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (PreferenceManager(requireContext()).getString(Constants.KEY_USER_TYPE) == Constants.KEY_PARTICIPANT) {
            binding.recyclerViewApplications.visibility = View.VISIBLE
            binding.recyclerViewSponsorBids.visibility = View.GONE
            listenApplications()
        } else if (PreferenceManager(requireContext()).getString(Constants.KEY_USER_TYPE) == Constants.KEY_SPONSOR) {
            binding.recyclerViewApplications.visibility = View.GONE
            binding.recyclerViewSponsorBids.visibility = View.VISIBLE
            listenSponsorBids()
        }

//        listenSponsorBids()
    }

    private fun listenApplications() {

        database.collection(Constants.KEY_COLLECTION_PAYMENTS)
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }
    }

    private fun listenSponsorBids() {
        database.collection(Constants.KEY_TARGET_BIDS)
            .addSnapshotListener { value, error ->
                targetEventLisener(value, error)
            }
    }

    private fun targetEventLisener(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null)
            return
        if (value != null) {
            applicationsList.clear()
            value.documentChanges.forEach {
                if (it.type == DocumentChange.Type.MODIFIED || it.type == DocumentChange.Type.ADDED) {

                    if (it.document.getString(Constants.KEY_USER_ID) == PreferenceManager(
                            requireContext()
                        ).getString(Constants.KEY_USER_ID)
                    ) {

                        val stallTitle = it.document.getString(Constants.KEY_BID_TITLE).toString()
                        val stallStatus = it.document.getBoolean(Constants.KEY_BID_STATUS)
                        val stallBidders = it.document.getDouble(Constants.KEY_BIDDERS)
                        val maxBid = it.document.getDouble(Constants.KEY_MAX_BID)
                        val minBid = it.document.getDouble(Constants.KEY_MIN_BID)
                        val description =
                            it.document.getString(Constants.KEY_DESCRIPTION_1).toString()
                        val description_2 =
                            it.document.getString(Constants.KEY_DESCRIPTION_2).toString()
                        val description_4 =
                            it.document.getString(Constants.KEY_DESCRIPTION_4).toString()


                        targetBidsList.add(
                            StallBid(
                                stallTitle,
                                stallBidders!!,
                                stallStatus!!,
                                maxBid!!,
                                minBid!!,
                                description,
                                description_2,
                                description_4,
                                it.document.id
                            )
                        )

                    }




                    Log.d(TAG, "eventListener: ${it.document.id}")

                }
            }




            targetRecyclerView = binding.recyclerViewSponsorBids
            stallBidsAdapter = StallBidsAdapter(targetBidsList, this)
            targetRecyclerView.adapter = stallBidsAdapter

        }

    }


    private fun eventListener(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null)
            return
        if (value != null) {
            applicationsList.clear()
            value.documentChanges.forEach {
                if (it.type == DocumentChange.Type.MODIFIED || it.type == DocumentChange.Type.ADDED) {

                    if (it.document.getString(Constants.KEY_USER_ID) == PreferenceManager(
                            requireContext()
                        ).getString(Constants.KEY_USER_ID)
                    ) {
                        val applicationName = it.document.getString(Constants.KEY_NAME).toString()
                        val teamMembers =
                            it.document.getString(Constants.KEY_TEAM_MEMBERS).toString()
                        val teamName = it.document.getString(Constants.KEY_TEAM_NAME).toString()
                        val status = it.document.getBoolean(Constants.KEY_PAYMENT_CONFIRMATION)!!


                        applicationsList.add(
                            ApplicationStatus(
                                applicationName, teamName, teamMembers, status
                            )
                        )
                    }




                    Log.d(TAG, "eventListener: ${it.document.id}")

                }
            }


            applicationStatusAdapter = ApplicationStatusAdapter(applicationsList, this)
            recyclerView.adapter = applicationStatusAdapter

        }
    }

    override fun onItemClicked(stallBid: StallBid) {
        val intent = Intent(requireActivity(), DetailStallActivity::class.java)
        intent.putExtra(Constants.KEY_STALL_BID, stallBid)
        startActivity(intent)
    }

}