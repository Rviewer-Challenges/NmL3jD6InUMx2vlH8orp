package com.braiso_22.firebase_chat.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class TextUtilsKtTest {

    @Test
    fun `empty email should return false`(){
        val result = isEmail("")
        assertThat(result).isFalse()
    }

    @Test
    fun `malformed email should return false`(){
        val result = isEmail("braisfv22@.gmail.com")
        assertThat(result).isFalse()
    }

    @Test
    fun `well formed email should return true`(){
        val result = isEmail("braisfv22@gmail.com")
        assertThat(result).isTrue()
    }

    @Test
    fun `empty password should return false`(){
        val result = isPassword("")
        assertThat(result).isFalse()
    }
    @Test
    fun `password of less than 8 characters should return false`(){
        val result = isPassword("123")
        assertThat(result).isFalse()
    }
    @Test
    fun `password without 1 or more lowecase should return false`(){
        val result = isPassword("PASSWO1.")
        assertThat(result).isFalse()
    }
    @Test
    fun `password without 1 or more uppercase should return false`(){
        val result = isPassword("passwo1.")
        assertThat(result).isFalse()
    }
    @Test
    fun `password without 1 or more numbers should return false`(){
        val result = isPassword("Passwor.")
        assertThat(result).isFalse()
    }
    @Test
    fun `password without 1 or more special chars should return false`(){
        val result = isPassword("Passwor1")
        assertThat(result).isFalse()
    }

    @Test
    fun `well formed password should return true`(){
        val result = isPassword("Password1234.")
        assertThat(result).isTrue()
    }
}