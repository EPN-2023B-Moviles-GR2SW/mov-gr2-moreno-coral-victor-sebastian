package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b2023gr2sw.ui.theme.BBaseDatosMemoria

class FRecyclerView : AppCompatActivity() {
    var totallikes=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)
        inicializarRecyclerView()
    }

    fun inicializarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adaptador= FRecyclerViewAdaptadorNombreDescripcion(
        this, // Contexto
        BBaseDatosMemoria.arregloBEntrenador, // Arreglo datos
        recyclerView // Recycler view
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes()
    {
        totallikes = totallikes + 1
        val totallikesTextView = findViewById<TextView>(
            R.id.tv_total_likes
        )
        totallikesTextView.text = totallikes.toString()
    }
}