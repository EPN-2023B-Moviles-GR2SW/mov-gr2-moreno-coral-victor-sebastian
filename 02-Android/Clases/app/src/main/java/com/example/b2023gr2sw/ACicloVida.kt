package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("Hola")
        mostrarSnackbar("OnCreate")

    }
    override fun onResume() { super.onResume()
        mostrarSnackbar( "onResume")

    }

    override fun onRestart() { super.onRestart()
        mostrarSnackbar( "onRestart") }

    override fun onPause() { super.onPause()
        mostrarSnackbar ("onPause")
    }

    override fun onStop() { super.onStop()
        mostrarSnackbar( "onStop")
    }

    override fun onDestroy() { super.onDestroy()
        mostrarSnackbar( "onDestroy")
    }

    fun mostrarSnackbar(texto: String) {
        textoGlobal += texto
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_main),
            textoGlobal, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}