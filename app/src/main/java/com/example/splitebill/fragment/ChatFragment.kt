package com.example.splitebill.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitebill.R
import com.example.splitebill.adapter.FriendAdapter
import com.example.splitebill.model.Friend
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.item_friends.*


class ChatFragment : Fragment() {
    val userList = ArrayList<Friend>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        view.friendsRecyclerView.layoutManager = LinearLayoutManager(activity)



        //userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))

        getUserList(view)





        return view
    }

    fun getUserList(view: View){
        val firebase:FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val  databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (dataSnapShot:DataSnapshot in snapshot.children){
                    val friend = dataSnapShot.getValue(Friend::class.java)

                    if (!friend!!.userId.equals(firebase.uid)){

                        userList.add(friend)
                    }

                }
                view.friendsRecyclerView.adapter = FriendAdapter(requireActivity(),userList)
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(requireContext()!!.applicationContext,error.message,Toast.LENGTH_SHORT).show()

            }

        })

    }

}