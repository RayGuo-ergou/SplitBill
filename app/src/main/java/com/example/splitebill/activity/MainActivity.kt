package com.example.splitebill.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.splitebill.R
import com.example.splitebill.fragment.ChatFragment
import com.example.splitebill.fragment.HomepageFragment
import com.example.splitebill.fragment.SearchFragment
import com.example.splitebill.fragment.UserFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get all teh fragment for the for the app
        val homeFragment = HomepageFragment()
        val searchFragment = SearchFragment()
        val chatFragment = ChatFragment()
        val userFragment = UserFragment()
        //set the homepage to home fragment
        setCurrentFragment(homeFragment)

        //when click the bottom nav bar, change the fragment
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miMessage -> setCurrentFragment(chatFragment)
                R.id.miProfile -> setCurrentFragment(userFragment)
                R.id.miSearch -> setCurrentFragment(searchFragment)
            }
            true
        }
    }

    //init the navigate bar
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || super.onSupportNavigateUp()
    }

    //method to get the current fragment
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}