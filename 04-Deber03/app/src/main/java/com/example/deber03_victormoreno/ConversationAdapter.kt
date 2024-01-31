package com.example.deber03_victormoreno

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ConversationAdapter(conversations: List<Conversation>, context: Context) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {
    private val conversations: List<Conversation>
    private val context: Context

    init {
        this.conversations = conversations
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation: Conversation = conversations[position]
        holder.bind(conversation)
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView
        private val contactNameTextView: TextView
        private val lastMessageTextView: TextView

        init {
            avatarImageView = itemView.findViewById(R.id.avatarImageView)
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView)
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView)
        }

        fun bind(conversation: Conversation) {
            // Aquí asigna los datos del objeto Conversation a las vistas
            contactNameTextView.setText(conversation.getContactName())
            lastMessageTextView.setText(conversation.getLastMessage())
            // Puedes cargar la imagen del avatar usando alguna biblioteca como Glide o Picasso
        }
    }
}
