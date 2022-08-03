package com.kotlin.chatapplication.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.api.Context
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.model.Message
import org.w3c.dom.Text

class MessageAdapter(private val activity:Activity, private val list:List<Message>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val ITEM_RECEVIVE=1
    private val ITEM_SENT=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==ITEM_SENT){
            SentMessageViewHolder(LayoutInflater
                .from(activity)
                .inflate(R.layout.sent_message_item,parent,false))

        } else {
            ReceiveMessageViewHolder(LayoutInflater
                .from(activity)
                .inflate(R.layout.receive_message_item,parent,false))

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].senderId== FirebaseAuth.getInstance().currentUser?.uid){
            ITEM_SENT
        }else{
            ITEM_RECEVIVE
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.javaClass==SentMessageViewHolder::class.java){

            holder as SentMessageViewHolder
            holder.sentMessage.text=list[position].message
        }else {

            holder as ReceiveMessageViewHolder
            holder.receiveMessage.text=list[position].message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentMessage:TextView=itemView.findViewById(R.id.sentMessage)
    }


    class ReceiveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage:TextView=itemView.findViewById(R.id.receiveMessage)

    }

}