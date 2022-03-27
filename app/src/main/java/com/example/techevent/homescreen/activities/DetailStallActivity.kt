package com.example.techevent.homescreen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.techevent.databinding.ActivityDetailStallBinding
import com.example.techevent.homescreen.model.StallBid
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore

class DetailStallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStallBinding
    private lateinit var stallBid: StallBid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stallBid = intent.getSerializableExtra(Constants.KEY_STALL_BID) as StallBid

        binding.tvName.text = stallBid.stallTitle
        binding.tvBasePrice.text = "Base Bid: ${stallBid.minBid.toString()}"
        binding.tvRecentBit.text = "Recent Bid: ${stallBid.maxBid.toString()}"
        binding.tvTotalBids.text = "Total Bids: ${stallBid.noOfBidders.toString()}"

        binding.btnPostBid.setOnClickListener {

            postNewBid()
        }
    }

    private fun postNewBid() {

        val database: FirebaseFirestore = FirebaseFirestore.getInstance()

        if (binding.etBidPrice.editText?.text.toString().toInt() > stallBid.maxBid) {

            database.collection(Constants.KEY_STALL_BIDS)
                .document(stallBid.bidID)
                .update(
                    Constants.KEY_MAX_BID,
                    binding.etBidPrice.editText?.text.toString().toDouble()
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "Bid post", Toast.LENGTH_SHORT).show()

                    binding.tvTotalBids.text = "Total Bids: ${stallBid.noOfBidders.toInt().inc()}"
                    binding.tvRecentBit.text =
                        "Recent Bid: ${binding.etBidPrice.editText?.text.toString()}"
                    binding.tvBasePrice.text = "Base Bid: ${stallBid.minBid}"
                }

            database.collection(Constants.KEY_STALL_BIDS)
                .document(stallBid.bidID)
                .update(Constants.KEY_BIDDERS, (stallBid.noOfBidders.toInt() + 1).toDouble())

            val targetBids: HashMap<String, Any> = HashMap()
            targetBids[Constants.KEY_BID_STATUS] = stallBid.bidStatus
            targetBids[Constants.KEY_USER_ID] =
                PreferenceManager(this).getString(Constants.KEY_USER_ID).toString()
            targetBids[Constants.KEY_BID_TITLE] = stallBid.stallTitle
            targetBids[Constants.KEY_BIDDERS] = stallBid.noOfBidders
            targetBids[Constants.KEY_MIN_BID] = stallBid.minBid
            targetBids[Constants.KEY_MAX_BID] = stallBid.maxBid
            targetBids[Constants.KEY_DESCRIPTION_1] = stallBid.desc_1
            targetBids[Constants.KEY_DESCRIPTION_2] = stallBid.desc_2
            targetBids[Constants.KEY_DESCRIPTION_4] = stallBid.desc_3
            database.collection(Constants.KEY_TARGET_BIDS)
                .add(targetBids)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "This Bid has been added to your Target",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this, "Enter a greater Bid", Toast.LENGTH_SHORT).show()
        }


    }
}