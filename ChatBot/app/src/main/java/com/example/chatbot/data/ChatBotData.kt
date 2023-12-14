package com.example.chatbot.data


enum class UserType {
    BOT,
    USER
}

data class Message(
    val userType: UserType,
    val content: String
)