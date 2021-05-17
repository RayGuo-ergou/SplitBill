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
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.coroutines.selects.select


class AddEventActivity : AppCompatActivity() {

    val userList = ArrayList<User>()
    var selectedUserList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)



        getUserList()



        btnTest.setOnClickListener(){

            insertIntoDatabase()


        }
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



        when{

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
            }
            else ->{
                if (selectedUserList.size == 0){
                    Toast.makeText(
                        this@AddEventActivity,
                        "Please select people for the event",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                    val currentUid:String = firebase.uid

                    var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("Events")

                    val nameEvent: String = eventName.text.toString().trim { it <= ' ' }
                    val descriptionEvent: String = eventDescription.text.toString().trim { it <= ' ' }
                    val amountTotal: String = totalAmount.text.toString().trim { it <= ' ' }
                    val count:Int = selectedUserList.size+1
                    val randomNumber = (0..100000000).random()

                    var hashMap: HashMap<String, String> = HashMap()

                    hashMap.put("EventNo", randomNumber.toString())
                    hashMap.put("UserId",currentUid)
                    hashMap.put("EventName", nameEvent)
                    hashMap.put("EventDescription", descriptionEvent)
                    hashMap.put("TotalAmount",amountTotal)
                    hashMap.put("People", count.toString())

                    reference!!.child(currentUid).child(randomNumber.toString()).setValue(hashMap)

                    for (users:User in selectedUserList){
                        hashMap.clear()
                        var friendUid = users.userId
                        hashMap.put("EventNo", randomNumber.toString())
                        hashMap.put("UserId",friendUid)
                        hashMap.put("EventName", nameEvent)
                        hashMap.put("EventDescription", descriptionEvent)
                        hashMap.put("TotalAmount",amountTotal)
                        hashMap.put("People", count.toString())

                        reference!!.child(friendUid).child(randomNumber.toString()).setValue(hashMap)
                    }




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