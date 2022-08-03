package com.kotlin.chatapplication.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.`interface`.OnClickUserItem
import com.kotlin.chatapplication.model.User

class UserAdapter(private val activity: Activity, private val userList: List<User>
,private val onClick: OnClickUserItem
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            LayoutInflater
                .from(activity)
                .inflate(R.layout.user_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name.text=userList[position].name

        holder.itemView.setOnClickListener{
            onClick.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

