package com.braiso_22.firebase_chat.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.braiso_22.firebase_chat.model.Message
import com.braiso_22.firebase_chat.model.User
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(route = "chat")
fun ChatScreen(otherUser: User, navigator: DestinationsNavigator) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = otherUser.name, fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = {
                    navigator.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            })
    },
        bottomBar = { messageBox() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            messageList(otherUser)
        }
    }
}

@Composable

fun sentMessage(message: Message) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(100.dp, end = 10.dp)
            .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp))
            .background(color = Color.LightGray)

    ) {
        Row(Modifier.padding(10.dp)) {
            Column(modifier = Modifier.weight(3.0f, true)) {
                Text(
                    text = message.message,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun receivedMessage(message: Message) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp, end = 100.dp)
            .clip(RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp))
            .background(color = Color.Yellow)

    ) {
        Row(Modifier.padding(10.dp)) {
            Column(modifier = Modifier.weight(3.0f, true)) {
                Text(
                    text = message.message,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun messageList(user: User) {
    val listMessage = mutableListOf<Message>(Message("", ""))

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(listMessage) { _, message ->
            Spacer(modifier = Modifier.height(8.dp))
            if (message.email == user.email)
                sentMessage(message = message)
            else
                receivedMessage(message = message)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun messageBox() {
    val textState = remember {
        mutableStateOf(TextFieldValue())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = Color.LightGray,
                )
                .weight(1f, true)
                .padding(16.dp),
            value = textState.value,
            onValueChange = { textState.value = it }
        )

        Spacer(modifier = Modifier.padding(12.dp))
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "send")
        }
    }
}

