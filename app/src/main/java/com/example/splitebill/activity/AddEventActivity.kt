package com.example.splitebill.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitebill.R
import com.example.splitebill.adapter.CheckboxAdapter
import com.example.splitebill.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*

class AddEventActivity : AppCompatActivity() {

    val userList = ArrayList<User>()
    var selectedUserList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)



        getUserList()

        insertIntoDatabase()
    }

    private fun getUserList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {

                        userList.add(user)
                    }
                }
                val checkboxAdapter = CheckboxAdapter(userList)
                checkboxRecyclerView.adapter = checkboxAdapter

                selectedUserList = checkboxAdapter.listOfSelected()


            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()

            }


        })

    }
    //TODO :Add data to database
    private fun insertIntoDatabase(){

        btnTest.setOnClickListener(){

            val intent = Intent(this@AddEventActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            Log.d("List", selectedUserList.toString())
            startActivity(intent)
            finish()
        }

    }
}