package com.braiso_22.firebase_chat.utils

import java.util.regex.Pattern.compile

private val emailRegex = compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
private val passwordRegex = compile(
    "(?=.*[a-z])(?=.*[A-Z])(?=.*(\\d))(?=.*(\\W)).{8,}"
)
fun isEmail(string: String): Boolean {
    return string.isNotEmpty() && emailRegex.matcher(
        string
    ).matches()
}

fun isPassword(string:String):Boolean{
    return string.isNotEmpty() && passwordRegex.matcher(
        string
    ).matches()
}