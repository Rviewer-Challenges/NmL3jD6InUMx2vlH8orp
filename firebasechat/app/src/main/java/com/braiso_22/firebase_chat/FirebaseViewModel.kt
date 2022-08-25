package com.braiso_22.firebase_chat

import android.util.Log
import com.braiso_22.firebase_chat.model.Chat
import com.braiso_22.firebase_chat.model.Message
import com.braiso_22.firebase_chat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

val firebaseViewModel = FirebaseViewModel()

class FirebaseViewModel {
    private val usersCollection = Firebase.firestore.collection("users")
    private val chatsCollection = Firebase.firestore.collection("chats")


    fun getAllUsers(func: (List<User>) -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = usersCollection.get().await()
            val arrayUsers = mutableListOf<User>()
            for (document in querySnapshot.documents) {
                arrayUsers.add(document.toObject<User>()!!)
            }
            func(arrayUsers)

        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
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

    private fun getDocIdByEmail(email: String, func: (String) -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot =
                    usersCollection.whereEqualTo("email", email).limit(1).get().await()

                for (document in querySnapshot.documents) {
                    func(document.id)
                }

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }

    fun creteChatWith(user: User) =
        CoroutineScope(Dispatchers.IO).launch {
            var currentId = ""
            var otherId = ""

            firebaseViewModel.getDocIdByEmail(Firebase.auth.currentUser?.email!!) { id ->
                currentId = id
            }.join()

            firebaseViewModel.getDocIdByEmail(user.email) { id ->
                otherId = id
            }.join()

            val id1 = "$currentId-$otherId"
            val id2 = "$otherId-$currentId"

            val querySnapshot = chatsCollection.get().await()
            for (document in querySnapshot.documents) {
                if (document.id == id1 || document.id == id2) {
                    return@launch
                }
            }

            chatsCollection.document(id1).set(Chat())
        }

    fun subscribeToRealtimeMessages(user: User, setData: (list: MutableList<Message>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var currentId = ""
            var otherId = ""
            runBlocking {
                firebaseViewModel.getDocIdByEmail(Firebase.auth.currentUser?.email!!) { id ->
                    currentId = id
                }
            }.join()
            runBlocking {
                firebaseViewModel.getDocIdByEmail(user.email) { id ->
                    otherId = id
                }
            }.join()


            val id1 = "$currentId-$otherId"
            val id2 = "$otherId-$currentId"
            val id = if (!chatsCollection.document(id1).get().await().exists()) {
                id2
            } else {
                id1
            }

            val querySnapshot =
                chatsCollection.document(id)
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        var mapMessages: ArrayList<HashMap<String, String>>
                        var messages = mutableListOf<Message>()
                        firebaseFirestoreException?.let {
                            it.message?.let { it1 -> Log.e(this.toString(), it1) }
                            return@addSnapshotListener
                        }
                        querySnapshot?.let {
                            mapMessages = it?.get("messages")?.let { message ->
                                message as ArrayList<HashMap<String, String>>
                            } ?: run {
                                ArrayList()
                            }

                            for (message in mapMessages)
                                messages.add(
                                    Message(message["message"]!!, message["email"]!!)
                                )
                            setData(messages)
                        }
                    }
        }
    }

    fun saveMessage(message: String, user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            var currentId = ""
            var otherId = ""
            runBlocking {
                firebaseViewModel.getDocIdByEmail(Firebase.auth.currentUser?.email!!) { id ->
                    currentId = id
                }
            }.join()
            runBlocking {
                firebaseViewModel.getDocIdByEmail(user.email) { id ->
                    otherId = id
                }
            }.join()


            val id1 = "$currentId-$otherId"
            val id2 = "$otherId-$currentId"
            val id = if (!chatsCollection.document(id1).get().await().exists()) {
                id2
            } else {
                id1
            }
            val messages = chatsCollection.document(id)
            try {
                messages.update(
                    "messages",
                    FieldValue.arrayUnion(Message(message, Firebase.auth.currentUser?.email!!))
                )
            } catch (e: Exception) {
                Log.e("Error", "No se pudo enviar el mensaje $e", e)
            }
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
            e.message?.let { Log.e("Error", it) }
        }
    }

    fun subscribeToRealtimeUpdates(setData: (list: MutableList<User>) -> Unit) {
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