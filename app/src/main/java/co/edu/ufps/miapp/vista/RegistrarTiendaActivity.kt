package co.edu.ufps.miapp.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import co.edu.ufps.miapp.R
import co.edu.ufps.miapp.modelo.Tienda
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarTiendaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_tienda)

        val nombre: TextInputEditText =findViewById(R.id.nombre_Tienda)
        val descripcion: TextInputEditText =findViewById(R.id.descripcion_Tienda)
        val telefono: TextInputEditText =findViewById(R.id.telefono_Tienda)
        val guardar: Button = findViewById(R.id.guardar)

        guardar.setOnClickListener(){
            guardar_Tienda(nombre.text.toString(),
                            descripcion.text.toString(),
                            telefono.text.toString())
        }
    }

    private fun guardar_Tienda(nombre: String, descripcion: String,  telefono: String
    ) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.reference
        val tienda= Tienda(myRef.push().key.toString(), nombre, descripcion, "", "", telefono, "")
        myRef.child("tienda").child(tienda.id).setValue(tienda)
    }
}