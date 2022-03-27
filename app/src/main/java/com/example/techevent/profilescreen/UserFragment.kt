package com.example.techevent.profilescreen

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.techevent.R
import com.example.techevent.authentication.AuthActivity
import com.example.techevent.databinding.FragmentUserBinding
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
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
        binding = FragmentUserBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bytes =
            Base64.decode(
                PreferenceManager(requireContext()).getString(Constants.KEY_IMAGE),
                Base64.DEFAULT
            )


        BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
            binding.profileImage.setImageBitmap(it)
        }

        binding.userEmail.text = preferenceManager.getString(Constants.KEY_EMAIL)
        binding.userName.text = preferenceManager.getString(Constants.KEY_NAME)
        binding.userType.text = preferenceManager.getString(Constants.KEY_USER_TYPE)

        binding
            .logout.setOnClickListener {
                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, false)
                startActivity(Intent(requireActivity(), AuthActivity::class.java))
                requireActivity().finish()
            }


    }
}