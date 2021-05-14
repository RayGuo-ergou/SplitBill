package com.example.splitebill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.splitebill.R
import com.example.splitebill.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_user.view.*

class MessageActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    var reference:DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val intent = intent
        val userId = intent.getStringExtra("UserId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        reference!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(User::class.java)
                //messageTextview.text = user!!.userName

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}