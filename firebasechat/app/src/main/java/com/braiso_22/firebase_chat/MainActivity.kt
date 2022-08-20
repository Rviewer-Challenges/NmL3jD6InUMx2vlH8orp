package com.braiso_22.firebase_chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.braiso_22.firebase_chat.screens.AuthenticationScreen
import com.braiso_22.firebase_chat.screens.NavGraphs
import com.braiso_22.firebase_chat.ui.theme.FirebasechatTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class MainActivity : ComponentActivity() {
    lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        setContent {
            FirebasechatTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

