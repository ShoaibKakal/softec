package com.example.techevent.authentication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.transition.TransitionInflater
import android.util.Base64
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.techevent.R
import com.example.techevent.databinding.FragmentSignupBinding
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream


const val TAG = "TestTag"

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private var enCodedImage: String? = null
    private lateinit var preferenceManager: PreferenceManager


    private lateinit var userType: AutoCompleteTextView
    private lateinit var adapterUserType: ArrayAdapter<String>
    private var filterUserType = "Any"


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
        binding = FragmentSignupBinding.inflate(layoutInflater)
        preferenceManager = PreferenceManager(requireContext())
        userType = binding.userType
        adapterUserType =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constants.userTypes)
        userType.setAdapter(adapterUserType)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        transition()
        binding.buttonRegister.setOnClickListener {

            if (isValidSignUpDetails()) {
                signUp()
            }
        }

        binding.layoutImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }

        userType.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position).toString()
            filterUserType = item
//            Toast.makeText(applicationContext, item, Toast.LENGTH_SHORT).show()
        }


        binding.textSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

    }


    private fun signUp() {
        loading(true)
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user: HashMap<String, Any> = HashMap()
        user[Constants.KEY_NAME] = binding.etName.editText?.text.toString()
        user[Constants.KEY_EMAIL] = binding.etEmail.editText?.text.toString()
        user[Constants.KEY_PASSWORD] = binding.etPassword.editText?.text.toString()
        user[Constants.KEY_IMAGE] = enCodedImage!!
        user[Constants.KEY_USER_TYPE] = filterUserType.toString()

        database.collection(Constants.KEY_COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener {
                loading(false)
                preferenceManager.putString(Constants.KEY_USER_ID, it.id)
                preferenceManager.putString(
                    Constants.KEY_NAME,
                    binding.etName.editText?.text.toString().trim()
                )
                preferenceManager.putString(Constants.KEY_IMAGE, enCodedImage!!)

                //Go to the next screen
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
            .addOnFailureListener {
                loading(false)
                showToast(it.message.toString())
            }

    }


    private val pickImage: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    val imageUri: Uri = it.data!!.data!!
                    try {
                        val inputStream: InputStream =
                            requireContext().contentResolver.openInputStream(imageUri)!!
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imageProfile.setImageBitmap(bitmap)
                        binding.textAddImage.visibility = View.GONE
                        enCodedImage = encodeImage(bitmap)
                    } catch (e: FileNotFoundException) {
                        Log.d(TAG, "${e.printStackTrace()}: ")
                    }
                }
            }
        }
    )

    private fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }


    private fun isValidSignUpDetails(): Boolean {

        if (enCodedImage == null) {
            showToast("Select profile image")
            return false
        } else if (binding.etName.editText?.text.toString().trim().isEmpty()) {
            showToast("Enter name")
            return false
        } else if (binding.etEmail.editText?.text.toString().trim().isEmpty()) {
            showToast("Enter email")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.editText?.text.toString())
                .matches()
        ) {
            showToast("Enter a valid email")
            return false
        } else if (filterUserType == "Any") {
            showToast("Select UserType")
            return false
        } else if (binding.etPassword.editText?.text.toString().trim().isEmpty()) {
            showToast("Enter password")
            return false
        } else if (binding.etConfirmPassword.editText?.text.toString().trim().isEmpty()) {
            showToast("Confirm your password")
            return false
        } else if (binding.etPassword.editText?.text.toString() != binding.etConfirmPassword.editText?.text.toString()) {
            showToast("Password & confirm password must be same")
            return false
        } else {
            return true
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.buttonRegister.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.buttonRegister.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

        }
    }


    private fun transition() {
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

}