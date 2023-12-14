package com.example.chatbot.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.screens.ChatScreen
import com.example.chatbot.screens.HomeScreen

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = Home.route ){
        composable(Home.route){
            HomeScreen(navController)
        }
        composable(Chat.route){
            ChatScreen(navController)
        }
    }
}