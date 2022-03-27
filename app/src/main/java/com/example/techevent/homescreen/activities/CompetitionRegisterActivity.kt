package com.example.techevent.homescreen.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.techevent.authentication.TAG
import com.example.techevent.databinding.ActivityCompetitionRegisterBinding
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class CompetitionRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompetitionRegisterBinding
    private var enCodedImage: String? = null
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompetitionRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        binding.imagePayment.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }

        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        binding.headText.text = "Registration For\n${intent.getStringExtra(Constants.KEY_NAME)}"


        binding.buttonRegister.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
//        loading(true)
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user: HashMap<String, Any> = HashMap()
        user[Constants.KEY_TEAM_NAME] = binding.etTeamName.editText?.text.toString()
        user[Constants.KEY_CNIC] = binding.etCnic.editText?.text.toString()
        user[Constants.KEY_USER_ID] = preferenceManager.getString(Constants.KEY_USER_ID).toString()
        user[Constants.KEY_PHONE_NUMBER] = binding.etPhoneNumber.editText?.text.toString()
        user[Constants.KEY_TEAM_MEMBERS] = binding.etTeamMembers.editText?.text.toString()
        user[Constants.KEY_AMOUNT] = binding.etAmount.editText?.text.toString()
        user[Constants.KEY_IMAGE] = enCodedImage!!
        user[Constants.KEY_NAME] = intent.getStringExtra(Constants.KEY_NAME).toString()
        user[Constants.KEY_PAYMENT_CONFIRMATION] = false

        database.collection(Constants.KEY_COLLECTION_PAYMENTS)
            .add(user)
            .addOnSuccessListener {
                loading(false)

                binding.etTeamName.visibility = View.GONE
                binding.etCnic.visibility = View.GONE
                binding.etPhoneNumber.visibility = View.GONE
                binding.etTeamMembers.visibility = View.GONE
                binding.etAmount.visibility = View.GONE
                binding.imagePayment.visibility = View.GONE
                binding.buttonRegister.visibility = View.GONE
                binding.headingUploadPayment.visibility = View.GONE
                binding.headText.text =
                    "Congradulation You have been Registered for ${intent.getStringExtra(Constants.KEY_NAME)}. Please wait for the Payment Confirmation"

            }
            .addOnFailureListener {
                loading(false)

            }
    }


    private val pickImage: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    val imageUri: Uri = it.data!!.data!!
                    try {
                        val inputStream: InputStream =
                            this.contentResolver.openInputStream(imageUri)!!
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imagePayment.setImageBitmap(bitmap)
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
        val previewWidth = 200
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
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


}