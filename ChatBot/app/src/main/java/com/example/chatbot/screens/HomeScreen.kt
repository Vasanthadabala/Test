package com.example.chatbot.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatbot.navigation.Chat

@Composable
fun HomeScreen(navController:NavHostController){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
    ) {
        Button(
            onClick = { navController.navigate(Chat.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(28),
            colors = ButtonDefaults.buttonColors(Color(0XFF1F8AFF))
        ) {
            Text(
                text = "Chat Bot",
                fontSize = 24.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}