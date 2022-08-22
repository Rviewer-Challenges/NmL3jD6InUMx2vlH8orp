package com.braiso_22.firebase_chat.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.braiso_22.firebase_chat.AuthenticationViewModel
import com.braiso_22.firebase_chat.R
import com.braiso_22.firebase_chat.screens.destinations.ChatScreenDestination
import com.braiso_22.firebase_chat.viewModel
import com.google.android.gms.common.api.ApiException
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

lateinit var launcher: ManagedActivityResultLauncher<Intent, ActivityResult>

@RootNavGraph(start = true)
@Destination(route = "auth")
@Composable
fun AuthenticationScreen(navigator: DestinationsNavigator) {
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
        checkDataButtons(navigator)
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
fun userDataInput() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        singleLineTextField(
            label = "Email",
            keyboardType = KeyboardType.Text,
            stringValue = email
        )
        Spacer(modifier = Modifier.padding(16.dp))
        singleLineTextField(
            label = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            stringValue = password
        )
    }
}
@Composable
fun checkDataButtons(navigator: DestinationsNavigator) {
    
    val localContext = LocalContext.current.applicationContext
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    if (isEmail(email.value) && isPassword(password.value)
                    ) {
                        viewModel.registerWithEmailAndPass(
                            email.value,
                            password.value
                        ) { isSuccessful ->
                            if (isSuccessful) {
                                navigator.navigate(ChatScreenDestination)
                            } else {
                                showAlert(context = localContext, "No se pudo registrar el usuario")
                            }
                        }
                    } else {
                        showAlert(context = localContext, "Usuario o contraseña invalidos")
                    }
                },
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
        GoogleButton(navigator = navigator)
    }
}

fun showAlert(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleButton(navigator: DestinationsNavigator) {
    val localContext = LocalContext.current.applicationContext
    launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            try {
                AuthenticationViewModel().signInWithGoogle(it.data) { isSusccesful ->
                    if (isSusccesful) {
                        navigator.navigate(ChatScreenDestination)
                    } else {
                        Toast.makeText(localContext,"Google sign in failed",Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(localContext,"Google sign is currently disabled",Toast.LENGTH_SHORT).show()
                Log.w("TAG", "Error, couldn't get credentials", e)
            }catch (e:Exception){
                Toast.makeText(localContext, "Unknown exception ", Toast.LENGTH_LONG).show()
                Log.w("TAG", "unhandled exception", e)
            }
        }

    Surface(
        onClick = {
            val intent = viewModel.loginWithGoogle(context = localContext)
            launcher.launch(intent)
        },
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