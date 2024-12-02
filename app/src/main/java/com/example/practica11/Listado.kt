package com.example.practica11

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Listado : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private val db = FirebaseFirestore.getInstance()
    private val coleccion = "Contactos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        linearLayout = findViewById(R.id.linearLayoutContactos)

        // Llamar la función para obtener los datos de Firestore
        obtenerContactos()
    }

    private fun obtenerContactos() {
        db.collection(coleccion)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val contacto = document.toObject(Contacto::class.java)
                    agregarContactoVista(contacto)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener los datos.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun agregarContactoVista(contacto: Contacto) {
        // Crear un TextView para mostrar el contacto
        val textView = TextView(this)
        textView.text = "Nombre: ${contacto.nombre}\nApellidos: ${contacto.apellidos}\nCorreo: ${contacto.correo}\n"
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.textSize = 16f

        // Agregar el TextView al LinearLayout
        linearLayout.addView(textView)
    }
}
