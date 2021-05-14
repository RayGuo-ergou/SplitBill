package com.example.splitebill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.splitebill.R
import com.example.splitebill.fragment.HomepageFragment
import com.example.splitebill.fragment.ChatFragment
import com.example.splitebill.fragment.SearchFragment
import com.example.splitebill.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*  val userId = intent.getStringExtra("user_id")
          val emailId = intent.getStringExtra("email_id")

          user_id.text = "User Id :: $userId"
          email_id.text = "Email Id :: $emailId"

          logout_btn.setOnClickListener{
              //log out
              FirebaseAuth.getInstance().signOut()

              startActivity(Intent(this@MainActivity, LoginActivity::class.java))
              finish()
          }*/
        val homeFragment = HomepageFragment()
        val searchFragment = SearchFragment()
        val chatFragment = ChatFragment()
        val userFragment = UserFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miMessage -> setCurrentFragment(chatFragment)
                R.id.miProfile -> setCurrentFragment(userFragment)
                R.id.miSearch -> setCurrentFragment(searchFragment)
            }
            true
        }
        bottomNavigationView.getOrCreateBadge(R.id.miMessage).apply {
            number = 10
            isVisible = true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || super.onSupportNavigateUp()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}