package com.kotlin.chatapplication.model

import java.util.*

class Message {
     var message:String? = null
     var senderId:String? =null
    var receiverId:String? = null
    var messageId:String? = null
    var currentTime:Date? =null

    constructor(){}

    constructor(message:String?,senderId:String?,receiverId:String?,messageId:String?,currentTime:Date?){
        this.message=message
        this.senderId=senderId
        this.receiverId=receiverId
        this.messageId=messageId
        this.currentTime=currentTime
    }

    override fun toString(): String {
        return "Message(message=$message, senderId=$senderId, receiverId=$receiverId, messageId=$messageId)"
    }


}