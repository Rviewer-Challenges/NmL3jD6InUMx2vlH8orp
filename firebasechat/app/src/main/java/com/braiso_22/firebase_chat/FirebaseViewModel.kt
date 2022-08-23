package com.braiso_22.firebase_chat

import android.util.Log
import com.braiso_22.firebase_chat.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

val firebaseViewModel = FirebaseViewModel()

class FirebaseViewModel {
    private val usersCollection = Firebase.firestore.collection("users")


    fun getAllUsers(func: (List<User>) -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = usersCollection.get().await()
            val arrayUsers = mutableListOf<User>()
            for (document in querySnapshot.documents) {
                arrayUsers.add(document.toObject<User>()!!)
            }
            func(arrayUsers)

        } catch (e: Exception) {

        }
    }

    fun getUserByEmail(email: String, func: (User) -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot =
                    usersCollection.whereEqualTo("email", email).limit(1).get().await()

                for (document in querySnapshot.documents) {
                    func(document.toObject<User>()!!)
                }

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }

    fun saveUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = usersCollection.get().await()
            for (document in querySnapshot.documents) {
                if (document.toObject<User>()!! == user) {
                    return@launch
                }
            }
            usersCollection.add(user)

        } catch (e: Exception) {

        }
    }

    fun suscribeToRealtimeUpdates(setData: (list: MutableList<User>) -> Unit) {
        val arrayUsers = mutableListOf<User>()
        usersCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                it.message?.let { it1 -> Log.e(this.toString(), it1) }
                return@addSnapshotListener
            }
            querySnapshot?.let {
                for (document in querySnapshot.documents) {
                    arrayUsers.add(document.toObject<User>()!!)
                }
                setData(arrayUsers)
                arrayUsers.clear()
            }
        }
    }
}