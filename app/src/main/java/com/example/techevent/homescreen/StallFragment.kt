package com.example.techevent.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techevent.authentication.TAG
import com.example.techevent.databinding.FragmentStallBinding
import com.example.techevent.homescreen.activities.DetailStallActivity
import com.example.techevent.homescreen.model.StallBid
import com.example.techevent.homescreen.model.StallBidsAdapter
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.example.techevent.untils.StallBidListiner
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class StallFragment : Fragment(), StallBidListiner {

    private lateinit var binding: FragmentStallBinding
    private lateinit var stallBidsAdapter: StallBidsAdapter
    private var stallsList = ArrayList<StallBid>()
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var stallBidsRecyclerView: RecyclerView
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
        binding = FragmentStallBinding.inflate(layoutInflater)
        stallBidsRecyclerView = binding.recyclerViewStallBids
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenBids()



    }

    private fun listenBids() {
        val database = FirebaseFirestore.getInstance()

        database.collection(Constants.KEY_STALL_BIDS)
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }
    }

    private fun eventListener(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null)
            return
        if (value != null) {
//            val count = chatMessages.size

            value.documentChanges.forEach {
                if (it.type == DocumentChange.Type.MODIFIED || it.type == DocumentChange.Type.ADDED) {

                    val stallTitle = it.document.getString(Constants.KEY_BID_TITLE).toString()
                    val stallStatus = it.document.getBoolean(Constants.KEY_BID_STATUS)
                    val stallBidders = it.document.getDouble(Constants.KEY_BIDDERS)
                    val maxBid = it.document.getDouble(Constants.KEY_MAX_BID)
                    val minBid = it.document.getDouble(Constants.KEY_MIN_BID)
                    val description = it.document.getString(Constants.KEY_DESCRIPTION_1).toString()
                    val description_2 = it.document.getString(Constants.KEY_DESCRIPTION_2).toString()


                    stallsList.add(
                        StallBid(
                            stallTitle,
                            stallBidders!!,
                            stallStatus!!,
                            maxBid!!,
                            minBid!!,
                            description,
                            description_2,
                            "5000",
                            it.document.id
                        )
                    )

                    Log.d(TAG, "eventListener: ${it.document.id}")

                }
            }


            stallBidsAdapter = StallBidsAdapter(stallsList, this)
            stallBidsRecyclerView.adapter = stallBidsAdapter

        }
    }



    override fun onItemClicked(stallBid: StallBid) {

        val intent = Intent(requireActivity(), DetailStallActivity::class.java)
        intent.putExtra(Constants.KEY_STALL_BID, stallBid)
        startActivity(intent)
    }

}