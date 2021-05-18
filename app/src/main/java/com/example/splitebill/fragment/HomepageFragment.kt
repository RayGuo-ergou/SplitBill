package com.example.splitebill.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitebill.R
import com.example.splitebill.activity.AddEventActivity
import com.example.splitebill.activity.MessageActivity
import com.example.splitebill.adapter.EventAdapter
import com.example.splitebill.model.Event
import com.example.splitebill.model.Friend
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_homepage.view.*


class HomepageFragment : Fragment(R.layout.fragment_homepage) {

    val eventList = ArrayList<Event>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)
        view.homepageRecyclerView.layoutManager = LinearLayoutManager(activity)

        getEventList(view)

        view.fabAdd.setOnClickListener { view ->
            //Toast.makeText(activity, "add fab touched", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, AddEventActivity::class.java)

            activity?.startActivity(intent)

        }

        return view
    }

    //get all envent
    private fun getEventList(view: View) {

        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Events").child(firebase.uid)

        //listen on firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val event = dataSnapShot.getValue(Event::class.java)

                    eventList.add(event!!)

                }
                view.homepageRecyclerView.adapter = EventAdapter(activity, eventList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }


}