package com.example.splitebill.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.splitebill.R
import com.example.splitebill.activity.AddEventActivity
import com.example.splitebill.activity.MessageActivity
import kotlinx.android.synthetic.main.fragment_homepage.view.*


class HomepageFragment : Fragment(R.layout.fragment_homepage) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)


         view.fabAdd.setOnClickListener { view ->
            Toast.makeText(activity, "add fab touched", Toast.LENGTH_SHORT).show()

             val intent = Intent(activity, AddEventActivity::class.java)

             activity?.startActivity(intent)

         }

        return view
    }


}