package com.braiso_22.firebase_chat.model

data class User(val email: String, val name: String) {
    constructor() : this("", "")
}