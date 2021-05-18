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
import de.hdodenhof.circleimageview.CircleImageView

class FriendAdapter(
    private val activity:
    FragmentActivity?, private val friendList: ArrayList<Friend>
) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = friendList[position]
        holder.textUserName.text = user.userName
        Glide.with(activity!!).load(user.userImage).placeholder(R.drawable.profile)
            .into(holder.imgUser)
        //holder.imgUser.setImageResource(R.drawable.profile)
        holder.layoutUser.setOnClickListener() {
            val intent = Intent(activity, MessageActivity::class.java)
            intent.putExtra("UserId", user.userId)
            activity?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textUserName: TextView = view.findViewById(R.id.friendName)
        val textMessage: TextView = view.findViewById(R.id.messagePreview)
        val imgUser: CircleImageView = view.findViewById(R.id.userImage)
        val layoutUser: LinearLayout = view.findViewById(R.id.layoutUser)

    }

}