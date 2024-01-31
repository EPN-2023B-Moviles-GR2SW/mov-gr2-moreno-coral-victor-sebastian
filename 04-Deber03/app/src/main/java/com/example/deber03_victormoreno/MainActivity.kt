package com.example.deber03_victormoreno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val conversations: MutableList<Conversation> = ArrayList<Conversation>()
        conversations.add(Conversation("John Doe", "Hello there!"))
        conversations.add(Conversation("Jane Doe", "What's up?"))
        // Agrega más conversaciones según sea necesario
        val adapter = ConversationAdapter(conversations, this)
        recyclerView.adapter = adapter
    }
}
