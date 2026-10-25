package com.example.data.model

data class UserProfile(
    val name: String,
    val birthDate: String, // format "yyyy-MM-dd"
    val birthTime: String = "", // e.g. "14:30" or ""
    val birthCity: String = "",
    val zodiacSign: ZodiacSign,
    val numerologyProfile: NumerologyProfile,
    val vedicProfile: VedicProfile? = null
)

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: ChatSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isStreaming: Boolean = false,
    val isVedic: Boolean = false
)

enum class ChatSender {
    USER, ORACLE
}
