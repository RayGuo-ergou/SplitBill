package com.example.splitebill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.example.splitebill.userId.Companion.localUserId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_register)
        signup_btn.setOnClickListener{
            when{
                //if user do not enter email
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter emails",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //if user do not enter password
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                //if user do not enter username
                TextUtils.isEmpty(editTextTextUsername.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter username",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //if user do not enter password confirm
                TextUtils.isEmpty(editTextTextPasswordConfirm.text.toString().trim{it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password confirm",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else ->{



                    val email: String = editTextTextEmailAddress.text.toString().trim{it<=' '}
                    val password: String = editTextTextPassword.text.toString().trim {it<=' '}

                    val username: String = editTextTextUsername.text.toString().trim{it<=' '}
                    val passwordConfirm: String = editTextTextPasswordConfirm.text.toString().trim {it<=' '}

                    if (!password.equals(passwordConfirm)){
                        Toast.makeText(
                            this@RegisterActivity,
                            "password does not match",
                            Toast.LENGTH_SHORT
                        ).show()

                    }else{
                        //create an instance and create a register a user with email and password
                        auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(this){ task ->
                                if (task.isSuccessful){
                                    //firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    val user: FirebaseUser? = auth.currentUser
                                    val userId: String = user!!.uid



                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Sign Up Successfully",
                                        Toast.LENGTH_SHORT

                                    ).show()
                                    localUserId = FirebaseAuth.getInstance().currentUser!!.uid


                                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                                    val hashMap:HashMap<String,String> = HashMap()
                                    hashMap.put("userId", userId)
                                    hashMap.put("userName", username)
                                    hashMap.put("profileImage","")
                                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                                        if(it.isSuccessful){
                                            //open home
                                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            /*intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                            intent.putExtra("email_id", email)*/


                                            startActivity(intent)
                                            finish()
                                        }
                                    }


                                }else{

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT

                                    ).show()
                                }                        }
                    }




                }


            }

        }


        login_btn.setOnClickListener{
            onBackPressed()
        }

    }



}