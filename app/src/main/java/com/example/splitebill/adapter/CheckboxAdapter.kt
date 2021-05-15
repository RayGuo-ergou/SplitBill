package com.example.splitebill.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.splitebill.R
import com.example.splitebill.activity.MessageActivity
import com.example.splitebill.model.Friend
import com.example.splitebill.model.User
import de.hdodenhof.circleimageview.CircleImageView

class CheckboxAdapter( private val userList: ArrayList<User>) :
    RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.textUserName.text = user.userName
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textUserName: TextView = view.findViewById(R.id.friendCheckbox)


    }

}