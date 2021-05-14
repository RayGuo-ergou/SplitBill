package com.example.splitebill.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitebill.R
import com.example.splitebill.adapter.FriendAdapter
import com.example.splitebill.model.Friend
import kotlinx.android.synthetic.main.fragment_chat.view.*


class ChatFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        view.friendsRecyclerView.layoutManager = LinearLayoutManager(activity)

        val userList = ArrayList<Friend>()

        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))
        userList.add(Friend("abc", "https://homepages.cae.wisc.edu/~ece533/images/cat.png"))


        view.friendsRecyclerView.adapter = FriendAdapter(requireActivity(),userList)



        return view
    }


}