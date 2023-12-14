package com.example.chatbot.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatbot.data.Message
import com.example.chatbot.data.UserType
import com.example.chatbot.navigation.TopBar

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(navController: NavHostController){
    Scaffold(
        topBar = {TopBar(name = "Chats",navController)}
    ){
        Column(modifier =  Modifier.padding(top = 60.dp)) {
            ChatScreenComponent()
        }

    }
}
@ExperimentalComposeUiApi
@Composable
fun ChatScreenComponent() {
    var userInput by remember { mutableStateOf(TextFieldValue()) }
    var chatMessages by rememberSaveable { mutableStateOf(listOf<Message>()) }

    val listState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(1f)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true,
            state = listState
        ) {
            items(chatMessages.reversed()) { message ->
                AnimatedChatMessage(message = message){ updatedMessage ->
                    chatMessages = chatMessages + updatedMessage
                }
            }
        }

        Divider(color = Color.Gray, thickness = 0.75.dp)
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    singleLine = true,
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = { Text(text = "Ask a question") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp),
                    shape = RoundedCornerShape(25),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0XFF1F8AFF),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.width(5.dp))

                IconButton(
                    onClick = {
                        handleUserInput(userInput.text) { message ->
                            chatMessages = chatMessages + message
                        }
                        userInput = TextFieldValue("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessage(message: Message) {
    val background = when (message.userType) {
        UserType.BOT -> Color(0XFFEDEDED)
        UserType.USER -> Color(0XFF1F8AFF)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = if (message.userType == UserType.BOT) Arrangement.Start else Arrangement.End
    ) {
        Card(
            elevation = CardDefaults.cardElevation(3.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(background),
            modifier = Modifier
                .fillMaxWidth(.75f)
                .padding(start = 10.dp, top = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = message.content,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(3.dp),
                    fontFamily = FontFamily.SansSerif,
                    color = if (message.userType == UserType.BOT) Color.Black else Color.White
                )
            }
        }
    }
}

@Composable
fun AnimatedChatMessage(message: Message,updateMessages: (Message) -> Unit) {
    AnimatedVisibility(
        visible = true, // Set to true to ensure that the animation runs every time
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(initialAlpha = 0f, animationSpec = tween(durationMillis = 500)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut(animationSpec = tween(durationMillis = 500)),
        modifier = Modifier.padding(10.dp)
    ) {
        Column {
            ChatMessage(message)
            if (message.userType == UserType.BOT) {
                // Display suggestions after the bot's message
                SuggestionsRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    suggestions = listOf("lazy", "bloating", "stagnation"),
                    onSuggestionClicked = { suggestion ->
                        handleUserInput(userInput = suggestion) { botmessage ->
                            updateMessages(botmessage)
                        }
                    }
                )
            }
        }
    }
}




fun handleUserInput(userInput: String, updateMessages: (Message) -> Unit) {

    // Add user's message to the chat
    updateMessages(Message(UserType.USER, userInput))

    // Basic logic to analyze user input and generate bot response
    val botResponse = when {
        userInput.contains("bloating") || userInput.contains("stomach pain") -> {
            "Avoid dairy products, pizza, ice cream, cheese, fatty, greasy foods, bakery items, alcohol and smoking.\n" +
                    "Avoid milk products Tea/Coffee, curd, butter milk, ghee.\n" +
                    "Take chicken, fish, sea foods.\n" +
                    "Take jaggery."
        }
        userInput.contains("constipation") || userInput.contains("weak digestion") -> {
            "Increase fiber intake with fruits, vegetables, and whole grains.\n" +
                    "Stay hydrated and drink plenty of water.\n" +
                    "Consider adding probiotics to your diet.\n" +
                    "Exercise regularly to improve digestion."
        }
        userInput.contains("lazy") || userInput.contains("depression") -> {
            "Stay hydrated and drink plenty of water.\n" +
                    "Exercise regularly to improve digestion.\n"
        }
        userInput.contains("stagnation") || userInput.contains("phlegm congestion") -> {
            "Avoid irritating foods, such as spicy, acidic (for example, fruit juices), fried or fatty foods.\n" +
                    "Avoid drinking any alcohol and smoking..\n" +
                    "Stay hydrated and drink plenty of water.\n" +
                    "Eat smaller and more frequent meals.."
        }
        userInput.contains("hello")|| userInput.contains("hey") -> {
            "Hello How Can I Help You?"
        }
        userInput.contains("hi") -> {
            "Hi How Can I Help You?"
        }
        else -> {
            "I'm sorry, I couldn't understand your specific health concern. Please provide more details."
        }
    }

    // Display bot response
    updateMessages(Message(UserType.BOT, botResponse))
}

@Composable
fun SuggestionsRow(
    modifier: Modifier = Modifier,
    suggestions: List<String>,
    onSuggestionClicked: (String) -> Unit
) {
    Row(
        modifier.padding(start = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        suggestions.forEach { suggestion ->
            TextButton(
                onClick = { onSuggestionClicked(suggestion) },
                colors = ButtonDefaults
                    .buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(18),
                modifier = Modifier.border(
                    width = 2.dp,
                    color = Color(0XFF1F8AFF),
                    shape = RoundedCornerShape(18)
                )
            ) {
                Text(
                    text = suggestion,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
