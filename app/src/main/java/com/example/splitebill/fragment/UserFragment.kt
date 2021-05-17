package com.example.splitebill.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.splitebill.R
import com.example.splitebill.activity.LoginActivity
import com.example.splitebill.activity.MessageActivity
import com.example.splitebill.model.Friend
import com.example.splitebill.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_user.view.*


class UserFragment : Fragment() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        view.signOutBtn.setOnClickListener {


            //log out
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(activity, LoginActivity::class.java)

            activity?.startActivity(intent)
            activity?.finish()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                view.userName.text = user!!.userName
                if (user.userImage == "") {
                    view.userImage.setImageResource(R.drawable.profile)
                } else {
                    Glide.with(activity!!).load(user.userImage)
                        .into(view.userImage)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

        return view
    }


}