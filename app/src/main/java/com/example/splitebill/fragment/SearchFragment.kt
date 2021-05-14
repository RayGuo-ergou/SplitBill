package com.example.splitebill.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.splitebill.R
import com.example.splitebill.activity.MessageActivity
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.button.setOnClickListener(){


        }
        return view

    }


}