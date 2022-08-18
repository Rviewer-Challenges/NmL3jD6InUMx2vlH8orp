package com.braiso_22.firebase_chat

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun AuthenticationScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                20.dp
            )
            .fillMaxSize()
    ) {
        userDataInput()
        Spacer(modifier = Modifier.padding(16.dp))
        checkDataButtons()
    }
}

@Composable
fun checkDataButtons() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { },
                modifier = Modifier.weight(10f)
            ) {
                Text("Login")
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { },
                modifier = Modifier.weight(10f)
            ) {
                Text("Register")
            }
        }
        Button(onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth()) {
            Text(text = "Google")
        }
    }
}

@Composable
fun userDataInput() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        singleLineTextField("Email")
        Spacer(modifier = Modifier.padding(16.dp))
        singleLineTextField("Password")
    }
}

@Composable
fun singleLineTextField(label: String) {
    var textFieldState by remember {
        mutableStateOf("")
    }
    TextField(
        value = textFieldState,
        label = { Text(label) },
        onValueChange = {
            textFieldState = it
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}