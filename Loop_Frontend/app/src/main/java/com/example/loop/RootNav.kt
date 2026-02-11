package com.example.loop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loop.ui.auth.loginScreen
import com.example.loop.ui.auth.registerScreen
import com.example.loop.ui.screens.TopicsScreen
import com.example.loop.ui.screens.profileScreen

@Composable
fun Navigation(
    navController : NavHostController = rememberNavController()
){
    val destination by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "signup",
    ){
        composable(route = "signup"){
            registerScreen(
                navController = navController,
                onLogin = {
                    navController.navigate(route = "login")
                }
            )
        }

        composable(route = "login") {
            loginScreen(
                onNavigateToRegister = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }

        composable(route = "dashboard") {
            TopicsScreen()
        }

        composable(route = "settings") {
            profileScreen(
                onNavigateBack = {
                    navController.navigate(route = "dashboard")
                }
            )
        }
    }
}