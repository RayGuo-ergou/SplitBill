package com.example.splitebill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.splitebill.R
import com.example.splitebill.model.User

class CheckboxAdapter( private val userList: ArrayList<User>) :
    RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {



    var selectedValues = ArrayList<User>()

    fun listOfSelected():  ArrayList<User>{
        return selectedValues
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkbox, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.textUserName.text = user.userName

        holder.textUserName.setOnClickListener(View.OnClickListener {
            if (holder.textUserName.isChecked) {
                selectedValues.add(user)
            } else {
                selectedValues.remove(user)
            }
        })

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textUserName: CheckBox = view.findViewById(R.id.friendCheckbox)


    }

}