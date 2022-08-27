package com.braiso_22.firebase_chat.model

import java.io.Serializable

data class User(val email: String, val name: String) : Serializable {
    constructor() : this("", "")
}