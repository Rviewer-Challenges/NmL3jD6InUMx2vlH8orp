package com.braiso_22.firebase_chat.model

data class Chat(val messages: ArrayList<Message>){
    constructor(): this(arrayListOf<Message>())
}