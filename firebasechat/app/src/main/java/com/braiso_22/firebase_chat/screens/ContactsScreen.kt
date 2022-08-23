package com.braiso_22.firebase_chat.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.braiso_22.firebase_chat.firebaseViewModel
import com.braiso_22.firebase_chat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Preview
@Destination(route = "contacts")
fun ContactsScreen() {
    var users = remember {
        mutableStateListOf<User>()
    }
    var username = remember {
        mutableStateOf("")
    }
    firebaseViewModel.getUserByEmail(Firebase.auth.currentUser?.email!!) { user ->
        username.value = user.name
    }

    firebaseViewModel.suscribeToRealtimeUpdates {
        users.clear()
        users.addAll(it)
    }
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Yellow)
        ) {
            Button(onClick = {}) {
            }

            Text(text = username.value, fontSize = 20.sp)
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(users) { _, item ->
                Row() {
                    userContact(user = item)
                }
            }

        }
    }

}

@Composable
fun userContact(user: User) {

    Column(modifier = Modifier
        .padding(8.dp, 0.dp)
        .fillMaxSize()
        .clickable {

        }) {
        Row() {
            Column() {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = user.name, fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = user.email)
                Spacer(modifier = Modifier.padding(2.dp))
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

    }
}

