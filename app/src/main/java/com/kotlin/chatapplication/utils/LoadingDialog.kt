package com.kotlin.chatapplication.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import com.kotlin.chatapplication.R


class LoadingDialog(val activity: Activity?)  {
    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        val inflater=activity?.layoutInflater
//        val dialogView=

        val builder:AlertDialog.Builder

//        val builder:AlertDialog.Builder()
  //      builder.setView(inflater?.inflate(R.layout.loading_item,null))
    }
//
//    fun LoadingDialogBar(context: Context?) {
//        val params = window!!.attributes
//        params.gravity = Gravity.CENTER_HORIZONTAL
//        window!!.attributes = params
//        setTitle(null)
//        setCancelable(false)
//       // setContentView(LayoutInflater.from(context))
////        setContentView(LayoutInflater.from(context))
//
////        setContentView(LayoutInflater.from(context)
////            .inflate(R.layout.custome_dialog, null))
//    }



}