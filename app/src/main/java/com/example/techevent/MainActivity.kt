package com.example.techevent

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.techevent.authentication.AuthActivity
import com.example.techevent.authentication.TAG
import com.example.techevent.databinding.ActivityMainBinding
import com.example.techevent.untils.Constants
import com.example.techevent.untils.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        navigationView = binding.navigationView


        //Drawer Navigation
        val drawerLayout = binding.drawerLayout
        binding.imageMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        loadUserDetails()

        drawerNavigation()
    }

    private fun drawerNavigation() {

        navigationView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menuProfile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.menuTargets -> {
                    Toast.makeText(this, "Targets", Toast.LENGTH_SHORT).show()

                }
                R.id.menuPrivacy -> {
                    Toast.makeText(this, "Targets", Toast.LENGTH_SHORT).show()
                }

                R.id.menuSupport -> {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:softech@gmail.com")
                        this.putExtra(Intent.EXTRA_SUBJECT, "Send Feedback")
                    }
                    startActivity(Intent.createChooser(emailIntent, "Send feedback"))
                }

                R.id.logOut -> {
                    PreferenceManager(this).putBoolean(Constants.KEY_IS_SIGNED_IN, false)
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }
            }
            true

        }


    }


    private fun loadUserDetails() {

        binding.tvUsername.text =
            "Welcome ${PreferenceManager(this).getString(Constants.KEY_NAME)} 👋"

        val drawerUserName =
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_drawer_name)
        drawerUserName.text = PreferenceManager(this).getString(Constants.KEY_NAME);

        val drawerEmail = navigationView.getHeaderView(0).findViewById<TextView>(R.id.email_address)
        drawerEmail.text = PreferenceManager(this).getString(Constants.KEY_EMAIL)

        val drawerImageView =
            navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image)

        val bytes =
            Base64.decode(PreferenceManager(this).getString(Constants.KEY_IMAGE), Base64.DEFAULT)


        BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
            drawerImageView.setImageBitmap(it)
        }

    }


    private fun setupBottomNavigation() {
        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
        val bottomNavigation = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        if (PreferenceManager(this).getString(Constants.KEY_USER_TYPE) == Constants.KEY_EVENT_MANAGER) {
            bottomNavigation.visibility = View.GONE
        }
    }

}