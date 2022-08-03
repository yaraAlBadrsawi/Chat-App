package com.kotlin.chatapplication.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.kotlin.chatapplication.adapter.MessageAdapter
import com.kotlin.chatapplication.constants.Constants
import com.kotlin.chatapplication.databinding.FragmentRoomChatBinding
import com.kotlin.chatapplication.model.Message
import java.text.SimpleDateFormat
import java.util.*

class RoomChatFragment : Fragment() {

    private lateinit var binding: FragmentRoomChatBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var receiverId: String
    private lateinit var messageId: String
    private lateinit var senderId:String
    private lateinit var messageList:MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiverId = arguments?.getString(Constants.USER_UID)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initObjects(inflater)
        senderId = FirebaseAuth.getInstance().currentUser?.uid!!

        getMessageFromDB()

        binding.btnSend.setOnClickListener {
            when {
                binding.etMessage.text.isEmpty() -> { binding.etMessage.error="type something!" }
            }
            addMessageToDB()
        }
        return binding.root
    }


    private fun initObjects(inflater: LayoutInflater) {
        this.binding = FragmentRoomChatBinding.inflate(inflater)
        firestore= FirebaseFirestore.getInstance()

    }

    @SuppressLint("SimpleDateFormat")
     private fun addMessageToDB() {
        messageId = UUID.randomUUID().toString()
        val formatter = SimpleDateFormat("HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        val currentTime: String = formatter.format(date)

        val message = Message(
            message = binding.etMessage.text.toString(),
            senderId = senderId,
            receiverId = receiverId,
            messageId = messageId,
            currentTime =currentTime

        )

        Toast.makeText(activity, "uid : $receiverId", Toast.LENGTH_LONG).show()
        firestore.collection(Constants.CHAT_COLLECTION)
            .document(messageId)
            .set(message)
            .addOnSuccessListener {
                binding.etMessage.text = null
                getMessageFromDB()
                Toast.makeText(activity, "message send", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener {
                Toast.makeText(activity, "message not send", Toast.LENGTH_LONG).show()
            }

    }

    private fun getMessageFromDB(){
        messageList= mutableListOf()

        getMessageInReceiver()
//        for (messages in it!!) {
//            messageList.add(messages.toObject(Message::class.java))
//            setMessageInRec()
//            Log.d("RoomChatFragment", "messages  : --> ${messages.toObject(Message::class.java)} ")
//        }
    }

    private fun getMessageInReceiver() {

        if(Constants.RECEIVER_ID==senderId&&Constants.SENDER_ID==receiverId
            ||Constants.SENDER_ID==senderId&&Constants.RECEIVER_ID==receiverId){
       firestore.collection(Constants.CHAT_COLLECTION)
           .get()
           .addOnSuccessListener {
               Toast.makeText(activity,"message get done",Toast.LENGTH_LONG).show()
           }

           .addOnFailureListener{

        }}
    }



    private fun getMessageInSender():Query{
       return firestore.collection(Constants.CHAT_COLLECTION)
            .whereEqualTo(Constants.SENDER_ID,senderId )
            .whereEqualTo(Constants.RECEIVER_ID,receiverId)

            }



    private fun setMessageInRec(){

        val adapter= activity?.let { MessageAdapter(it,messageList) }
        binding.rvChat.adapter=adapter
        val layoutManger=LinearLayoutManager(activity)
        binding.rvChat.layoutManager=layoutManger
    }


}
