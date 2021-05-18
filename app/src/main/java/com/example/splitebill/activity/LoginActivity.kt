package com.example.splitebill.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.splitebill.R
import com.google.firebase.auth.FirebaseAuth
import com.example.splitebill.model.userId.Companion.localUserId
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //if the user logged in then just go to the home page
        var firebaseUser: FirebaseUser? = null
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

        //if user does not have account click here to sing up
        signup_btn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        //login button to log the user in
        login_btn.setOnClickListener {
            when {
                //if user do not enter email
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter emails",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //if user do not enter password
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    //get the user input information
                    val email: String = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                    val password: String = editTextTextPassword.text.toString().trim { it <= ' ' }

                    //login
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {


                                Toast.makeText(
                                    this@LoginActivity,
                                    "Sign Up Successfully",
                                    Toast.LENGTH_SHORT

                                ).show()

                                localUserId = FirebaseAuth.getInstance().currentUser!!.uid


                                //after logged in go to homepage
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)
                                finish()
                            } else {

                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT

                                ).show()
                            }
                        }
                }


            }

        }
    }
}