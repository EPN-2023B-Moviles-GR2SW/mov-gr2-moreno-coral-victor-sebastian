package com.example.deber03_victormoreno

class Conversation(private val contactName: String, private val lastMessage: String) {

    fun getContactName(): String {
        return contactName
    }

    fun getLastMessage(): String {
        return lastMessage
    }
}
