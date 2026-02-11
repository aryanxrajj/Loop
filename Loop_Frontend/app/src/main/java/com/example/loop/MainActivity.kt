package com.example.loop

import com.example.loop.ui.auth.registerScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.loop.network.RetrofitInstance
import com.example.loop.ui.auth.loginScreen
import com.example.loop.ui.screens.TopicsScreen
import com.example.loop.ui.screens.profileScreen
import com.example.loop.ui.theme.LoopTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            LoopTheme() {
                val backgroundColor = MaterialTheme.colorScheme.background
                val isDarkTheme = androidx.compose.foundation.isSystemInDarkTheme()

                // Set status bar and navigation bar colors to match background
                SideEffect {
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        // When dark theme: use light icons (white)
                        // When light theme: use dark icons (black)
                        isAppearanceLightStatusBars = !isDarkTheme
                        isAppearanceLightNavigationBars = !isDarkTheme
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
//                    Navigation()
//                    TopicsScreen()
                    Navigation()
                }



            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackendCheckScreen() {
    val scope = rememberCoroutineScope()
    var responseText by remember { mutableStateOf("Click the button to test backend!") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Backend Test") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = responseText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val response = RetrofitInstance.api.getUsers().execute()
                            if (response.isSuccessful) {
                                val body = response.body()
                                println("Backend Response: $body")
                                responseText = "Backend Working!\nResponse: $body"
                            } else {
                                println("Server responded with code ${response.code()} and message: ${response.message()}")
                                responseText = "Server Error: ${response.code()} ${response.message()}"
                            }
                        } catch (e: IOException) {
                            println("Network Error: ${e.message}")
                            responseText = "Network Error: ${e.message}"
                        } catch (e: HttpException) {
                            println("HTTP Exception: ${e.message}")
                            responseText = "HTTP Exception: ${e.message}"
                        } catch (e: Exception) {
                            println("Unexpected Error: ${e.message}")
                            responseText = "Unexpected Error: ${e.message}"
                        }
                    }
                }) {
                    Text("Check Backend")
                }
            }
        }
    )
}