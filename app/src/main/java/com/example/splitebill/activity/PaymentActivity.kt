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
import kotlin.math.log

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val intent = intent
        val userId = intent.getStringExtra("UserId")
        val refNo = intent.getStringExtra("refNo")

        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Events").child(userId!!)



        creditCardBtn.setOnClickListener {
            var creditCard = creditCardView.creditCardInfo
            Log.v("card",creditCard.cvv.toString())
            if (creditCard.cvv == ""||creditCard.name == ""|| creditCard.number == ""||
                    creditCard.expirationMonth == ""|| creditCard.expirationYear == ""){
                Toast.makeText(this, "Please enter the card information", Toast.LENGTH_SHORT).show()
            }else{
                databaseReference.child(refNo!!).removeValue()
                val intent = Intent(this@PaymentActivity, MainActivity::class.java)

                startActivity(intent)
                finish()
            }

//            databaseReference.child(refNo!!).removeValue()
//            val intent = Intent(this@PaymentActivity, MainActivity::class.java)
//
//            startActivity(intent)
//            finish()


        }

    }
}