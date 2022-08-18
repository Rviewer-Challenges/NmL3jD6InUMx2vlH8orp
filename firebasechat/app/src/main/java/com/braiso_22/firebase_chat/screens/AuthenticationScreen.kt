package com.braiso_22.firebase_chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        appLogo()
        Spacer(modifier = Modifier.padding(32.dp))
        userDataInput()
        Spacer(modifier = Modifier.padding(16.dp))
        checkDataButtons()
    }
}

@Composable
fun appLogo() {
    Image(
        painter = painterResource(id = R.drawable.text_with_logo),
        contentDescription = "App logo"
    )
    Text(text = "Chat", fontSize = 40.sp)
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
                Text("Login", fontSize = 16.sp)
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { },
                modifier = Modifier.weight(10f)
            ) {
                Text("Register", fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        GoogleButton()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleButton() {

    Surface(
        onClick = {},
        shape = Shapes().medium,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface
    ) {

        Row(
            modifier = Modifier.padding(start = 12.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign Up with Google", fontSize = 16.sp)
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