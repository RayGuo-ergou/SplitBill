package com.example.splitebill.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.splitebill.R
import com.example.splitebill.activity.MessageActivity
import com.example.splitebill.activity.PaymentActivity
import com.example.splitebill.model.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EventAdapter(private val activity:
                    FragmentActivity?, private val eventList: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Events").child(firebase.uid)
        holder.textName.text = event.EventName
        holder.textAmount.text = event.TotalAmount
        holder.textDes.text = event.EventDescription
        holder.textPeople.text = event.People

        holder.cancelBtn.setOnClickListener{
            databaseReference.child(event.EventNo).removeValue()
        }
        holder.payBtn.setOnClickListener{
            //databaseReference.child(event.EventNo).removeValue()
            val intent = Intent(activity, PaymentActivity::class.java)
            intent.putExtra("UserId",firebase.uid)
            intent.putExtra("refNo",event.EventNo)

            activity?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textName: TextView = view.findViewById(R.id.nameEvent)
        val textDes: TextView = view.findViewById(R.id.descriptionEvent)
        val textAmount: TextView = view.findViewById(R.id.eventAmount)
        val textPeople: TextView = view.findViewById(R.id.peopleNumber)
        val payBtn: Button = view.findViewById(R.id.payBtn)
        val cancelBtn: Button = view.findViewById(R.id.cancelBtn)

    }

}