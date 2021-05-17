package com.example.splitebill.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.splitebill.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val intent = intent
        val userId = intent.getStringExtra("UserId")
        val refNo = intent.getStringExtra("refNo")

        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Events").child(userId!!)

        var creditCard = creditCardView.creditCardInfo

        creditCardBtn.setOnClickListener {

            databaseReference.child(refNo!!).removeValue()
            val intent = Intent(this@PaymentActivity, MainActivity::class.java)

            startActivity(intent)
            finish()


        }

    }
}