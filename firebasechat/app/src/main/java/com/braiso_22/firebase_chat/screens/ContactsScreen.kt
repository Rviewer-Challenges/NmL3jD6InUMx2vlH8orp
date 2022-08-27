package com.braiso_22.firebase_chat.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.braiso_22.firebase_chat.authViewModel
import com.braiso_22.firebase_chat.firebaseViewModel
import com.braiso_22.firebase_chat.model.User
import com.braiso_22.firebase_chat.screens.destinations.AuthenticationScreenDestination
import com.braiso_22.firebase_chat.screens.destinations.ChatScreenDestination
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
@Destination(route = "contacts")
fun ContactsScreen(navigator: DestinationsNavigator) {
    var users = remember {
        mutableStateListOf<User>()
    }
    var username = remember {
        mutableStateOf("")
    }
    firebaseViewModel.getUserByEmail(Firebase.auth.currentUser?.email!!) { user ->
        username.value = user.name
    }

    firebaseViewModel.subscribeToRealtimeUpdates {
        users.clear()
        users.addAll(it)
    }
    val localContext = LocalContext.current.applicationContext
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = username.value, fontSize = 20.sp)
        },
            navigationIcon = {
                IconButton(onClick = {

                    authViewModel.signOutGoogle()
                    navigator.navigate(AuthenticationScreenDestination)
                    Toast.makeText(localContext, "Signed out", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
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
                    userContact(user = item, navigator)
                }
            }
        }
    }
}

@Composable
fun userContact(user: User, navigator: DestinationsNavigator) {
    Column(modifier = Modifier
        .padding(8.dp, 0.dp)
        .fillMaxSize()
        .clickable {
            firebaseViewModel.creteChatWith(user)
            navigator.navigate(ChatScreenDestination(user))

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

