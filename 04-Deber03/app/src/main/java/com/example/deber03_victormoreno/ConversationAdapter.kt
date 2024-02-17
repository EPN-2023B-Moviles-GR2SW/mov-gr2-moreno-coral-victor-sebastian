package com.example.deber03_victormoreno

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// La clase ConversationAdapter extiende RecyclerView.Adapter y está diseñada para mostrar una lista de Conversations en un RecyclerView.
class ConversationAdapter(conversations: List<Conversation>, context: Context) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    // Lista de conversaciones y contexto que se utilizan en la clase.
    private val conversations: List<Conversation>
    private val context: Context

    // Inicialización de las propiedades de la clase con los parámetros proporcionados.
    init {
        this.conversations = conversations
        this.context = context
    }

    // Método llamado cuando se necesita crear un nuevo ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        // Infla el diseño del elemento de la conversación y devuelve un nuevo ConversationViewHolder.
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    // Método llamado para vincular datos a una vista específica en una posición dada.
    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        // Obtiene la conversación en la posición dada y llama al método bind del ConversationViewHolder.
        val conversation: Conversation = conversations[position]
        holder.bind(conversation)
    }

    // Método que devuelve la cantidad total de elementos en la lista de conversaciones.
    override fun getItemCount(): Int {
        return conversations.size
    }

    // Clase interna que representa un elemento individual en la lista de conversaciones.
    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Vistas dentro de un elemento de la conversación.
        private val avatarImageView: ImageView
        private val contactNameTextView: TextView
        private val lastMessageTextView: TextView

        // Inicialización de las vistas utilizando findViewById.
        init {
            avatarImageView = itemView.findViewById(R.id.avatarImageView)
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView)
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView)
        }

        // Método utilizado para asignar datos del objeto Conversation a las vistas dentro del ViewHolder.
        fun bind(conversation: Conversation) {
            // Asigna el nombre del contacto y el último mensaje a las TextView.
            contactNameTextView.setText(conversation.getContactName())
            lastMessageTextView.setText(conversation.getLastMessage())
            // Puedes cargar la imagen del avatar usando alguna biblioteca como Glide o Picasso.
        }
    }
}
