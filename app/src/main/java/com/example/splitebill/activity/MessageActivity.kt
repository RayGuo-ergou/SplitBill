package com.example.splitebill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.splitebill.R
import com.example.splitebill.adapter.ChatAdapter
import com.example.splitebill.adapter.FriendAdapter
import com.example.splitebill.model.Chat
import com.example.splitebill.model.Friend
import com.example.splitebill.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_user.view.*

class MessageActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    val chatList = ArrayList<Chat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        //create the linear layout manager for the recycler view
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        //get the variables from the last activity
        val intent = intent
        val userId = intent.getStringExtra("UserId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        //get the user data so can display the user name in the chat window
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(User::class.java)
                //messageTextview.text = user!!.userName

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //when user send a message
        sendMessageBtn.setOnClickListener {
            var message: String = messageInput.text.toString()

            //to make sure user typed something
            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                messageInput.setText("")
            } else {
                //send message
                sendMessage(firebaseUser!!.uid, userId, message)
                messageInput.setText("")
            }
        }

        //call read message method
        readMessage(firebaseUser!!.uid, userId)

    }

    //function to send message
    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        //create a hashmap and then store that hashmap into database
        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat")
            .push()
            .setValue(hashMap)
    }

    //read the message
    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                chatList.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapshot.getValue(Chat::class.java)

                    //get the message from the current user and the user is talking to
                    if ((chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId)) ||
                        (chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId))
                    ) {
                        //add to the chat list
                        chatList.add(chat)
                    }
                    //implement the message recycle view
                    val chatAdapter = ChatAdapter(this@MessageActivity, chatList)
                    chatRecyclerView.adapter = chatAdapter
                    chatRecyclerView.scrollToPosition(chatList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}