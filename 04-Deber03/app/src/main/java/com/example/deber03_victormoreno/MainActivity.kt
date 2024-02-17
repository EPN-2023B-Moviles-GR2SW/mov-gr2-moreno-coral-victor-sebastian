package com.example.deber03_victormoreno

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del diseño
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Configurar el ActionBarDrawerToggle para manejar el icono de menú
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clic en el ícono de menú para abrir el menú lateral
        val menuIcon = findViewById<ImageView>(R.id.menuIcon)
        menuIcon.setOnClickListener { openDrawer() }

        // Configurar el manejador de clics en los elementos del menú lateral
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Manejar clics en elementos del menú lateral aquí
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // Acción para el item 1
                    // ...
                    true
                }
                R.id.menu_item2 -> {
                    // Acción para el item 2
                    // ...
                    true
                }
                // Agregar más casos según sea necesario
                else -> false
            }
        }

        // Configuración del RecyclerView y adaptador
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val conversations: MutableList<Conversation> = ArrayList<Conversation>()
        conversations.add(Conversation("John Doe", "Hello there!"))
        conversations.add(Conversation("Jane Doe", "What's up?"))
        conversations.add(Conversation("Alice", "Hi!"))
        conversations.add(Conversation("Bob", "Good to see you!"))
        val adapter = ConversationAdapter(conversations, this)
        recyclerView.adapter = adapter
    }

    // Método para abrir el menú lateral
    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}
