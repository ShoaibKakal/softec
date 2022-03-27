package com.example.techevent.authentication

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.techevent.MainActivity
import com.example.techevent.R
import com.example.techevent.databinding.FragmentLoginBinding
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        transition()

        binding.etEmail.setOnClickListener {

        }


        binding.textRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }



        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }


        binding.buttonLogin.setOnClickListener {

            if (isValidSignInDetails()) {
                singIn()
            }
        }
    }

    private fun singIn() {
        loading(true)
        val database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .whereEqualTo(Constants.KEY_EMAIL, binding.etEmail.editText?.text.toString().trim())
            .whereEqualTo(
                Constants.KEY_PASSWORD,
                binding.etPassword.editText?.text.toString().trim()
            )
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && it.result != null && it.result!!.documents.size > 0) {
                    val documentSnapshot = it.result!!.documents[0]
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.id)
                    preferenceManager.putString(
                        Constants.KEY_NAME,
                        documentSnapshot.getString(Constants.KEY_NAME)!!
                    )
                    preferenceManager.putString(
                        Constants.KEY_IMAGE,
                        documentSnapshot.getString(Constants.KEY_IMAGE)!!
                    )
                    preferenceManager.putString(
                        Constants.KEY_USER_TYPE,
                        documentSnapshot.getString(Constants.KEY_USER_TYPE)!!
                    )
                    preferenceManager.putString(
                        Constants.KEY_EMAIL,
                        documentSnapshot.getString(Constants.KEY_EMAIL)!!
                    )
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    loading(false)
                    showToast("Unable to sign In")
                }
            }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonLogin.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.buttonLogin.visibility = View.VISIBLE
        }
    }

    private fun isValidSignInDetails(): Boolean {
        return if (binding.etEmail.editText?.text.toString().trim().isEmpty()) {
            showToast("Enter email")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.editText?.text.toString())
                .matches()
        ) {
            showToast("Enter valid email")
            false
        } else if (binding.etPassword.editText?.text.toString().trim().isEmpty()) {
            showToast("Enter password")
            false
        } else {
            true
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


    private fun transition() {
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

}