package com.example.splitebill.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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

/*
This activity is used to add event (a bill to split with)
 */

class AddEventActivity : AppCompatActivity() {

    //set up the variables
    val userList = ArrayList<User>()
    var selectedUserList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        //create a linear layout recycle layout manager
        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)
        //call the function to get user list from firebase
        getUserList()

        //if the button clicked, insert the data into firebase database
        btnTest.setOnClickListener() {

            insertIntoDatabase()


        }
    }

    private fun getUserList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")

        //listen for the data changes
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //every time clear the list, avoid to have same data
                userList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    //if the user data is not the current user
                    if (!user!!.userId.equals(firebase.uid)) {

                        //add to the list for future diaplay
                        userList.add(user)
                    }
                }
                //call the recycle view adapter
                val checkboxAdapter = CheckboxAdapter(userList)
                checkboxRecyclerView.adapter = checkboxAdapter

                //get the users that been selected
                selectedUserList = checkboxAdapter.listOfSelected()


            }

            override fun onCancelled(error: DatabaseError) {

                //Toast error
                Toast.makeText(
                    applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()

            }


        })

    }


    private fun insertIntoDatabase() {

        when {
            //to make sure the user input everything
            TextUtils.isEmpty(eventName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddEventActivity,
                    "Please enter event name",
                    Toast.LENGTH_SHORT
                ).show()
            }
            TextUtils.isEmpty(eventDescription.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddEventActivity,
                    "Please enter event description",
                    Toast.LENGTH_SHORT
                ).show()
            }
            TextUtils.isEmpty(totalAmount.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddEventActivity,
                    "Please enter the total amount",
                    Toast.LENGTH_SHORT
                ).show()
            }//if the user input everything
            else -> {
                //if user did not select any other users
                if (selectedUserList.size == 0) {
                    Toast.makeText(
                        this@AddEventActivity,
                        "Please select people for the event",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {//if user selected at least one friend
                    val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                    val currentUid: String = firebase.uid

                    var reference: DatabaseReference? =
                        FirebaseDatabase.getInstance().getReference("Events")

                    val nameEvent: String = eventName.text.toString().trim { it <= ' ' }
                    val descriptionEvent: String =
                        eventDescription.text.toString().trim { it <= ' ' }
                    val amountTotal: String = totalAmount.text.toString().trim { it <= ' ' }
                    val count: Int = selectedUserList.size + 1
                    val randomNumber = (0..100000000).random()

                    //store the user input into a hashmap
                    var hashMap: HashMap<String, String> = HashMap()

                    hashMap.put("EventNo", randomNumber.toString())
                    hashMap.put("UserId", currentUid)
                    hashMap.put("EventName", nameEvent)
                    hashMap.put("EventDescription", descriptionEvent)
                    hashMap.put("TotalAmount", amountTotal)
                    hashMap.put("People", count.toString())

                    //store the data into database including all the other users that selected
                    reference!!.child(currentUid).child(randomNumber.toString()).setValue(hashMap)

                    for (users: User in selectedUserList) {
                        hashMap.clear()
                        var friendUid = users.userId
                        hashMap.put("EventNo", randomNumber.toString())
                        hashMap.put("UserId", friendUid)
                        hashMap.put("EventName", nameEvent)
                        hashMap.put("EventDescription", descriptionEvent)
                        hashMap.put("TotalAmount", amountTotal)
                        hashMap.put("People", count.toString())

                        reference!!.child(friendUid).child(randomNumber.toString())
                            .setValue(hashMap)
                    }


                    //go back to homepage
                    val intent = Intent(this@AddEventActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    Log.d("List", selectedUserList.toString())
                    Log.d("random", randomNumber.toString())
                    startActivity(intent)
                    finish()

                }
            }
        }


    }
}