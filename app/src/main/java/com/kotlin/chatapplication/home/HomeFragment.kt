package com.kotlin.chatapplication.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.`interface`.OnClickUserItem
import com.kotlin.chatapplication.adapter.UserAdapter
import com.kotlin.chatapplication.constants.Constants
import com.kotlin.chatapplication.databinding.FragmentHomeBinding
import com.kotlin.chatapplication.model.User

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var userList: MutableList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var firestore: FirebaseFirestore
    private val TAG: String = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initObjects(inflater)
        getUserFromFirestore()
        return binding.root
    }


    private fun initObjects(inflater: LayoutInflater) {
        this.binding = FragmentHomeBinding.inflate(inflater)
        this.firestore = FirebaseFirestore.getInstance()

    }

    private fun getUserFromFirestore() {
        firestore.collection(Constants.USER_COLLECTION)
            .get()
            .addOnSuccessListener {
                onSuccess(it)
            }
            .addOnFailureListener {
                onFailed(it)
            }
    }

    private fun onSuccess(it: QuerySnapshot?) {
        userList= mutableListOf()

        for (userData in it!!) {
            userList.add(userData.toObject(User::class.java))
        }

        binding.rec.layoutManager = LinearLayoutManager(activity)

        adapter= activity?.let { it1 ->
            UserAdapter(it1,userList, object : OnClickUserItem {
                override fun onItemClick(postion: Int) {
                    val bundle= bundleOf(Constants.USER_UID to userList[postion].uid)

                    Log.d(TAG, "onItemClick: user ID ->> ${bundle.getString(Constants.USER_UID)}")
                    findNavController().navigate(R.id.action_homeFragment_to_roomChatFragment,bundle)
                }
            })
        }!!

        binding.rec.adapter = adapter
    }

    private fun onFailed(it: Exception) {
        Toast.makeText(context,"Error occur! Try again \n$it",Toast.LENGTH_LONG).show()
        Log.d(TAG, "onFailed: $it")
    }


}


