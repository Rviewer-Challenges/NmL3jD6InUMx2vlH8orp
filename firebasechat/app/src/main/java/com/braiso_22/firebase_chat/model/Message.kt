package com.braiso_22.firebase_chat.model

data class Message(val message: String, val email: String) {
    constructor() : this("", "")
}