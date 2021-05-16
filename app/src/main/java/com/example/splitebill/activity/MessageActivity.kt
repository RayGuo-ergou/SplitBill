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

        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        val intent = intent
        val userId = intent.getStringExtra("UserId")

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(User::class.java)
                //messageTextview.text = user!!.userName

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        sendMessageBtn.setOnClickListener {
            var message: String = messageInput.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                messageInput.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userId, message)
                messageInput.setText("")
            }
        }

        readMessage(firebaseUser!!.uid, userId)

    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat")
            .push()
            .setValue(hashMap)
    }

    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                chatList.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapshot.getValue(Chat::class.java)

                    if ((chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId)) ||
                        (chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId))
                    ) {
                        chatList.add(chat)
                    }
                    val chatAdapter = ChatAdapter(this@MessageActivity, chatList)
                    chatRecyclerView.adapter = chatAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}