package com.example.chatbot.navigation

interface Destinations{
    val route:String
}
object Home: Destinations{
    override val route = "Home"
}
object Chat: Destinations{
    override val route = "Chat"
}