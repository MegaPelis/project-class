package co.edu.ufps.miapp.vista

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.ufps.miapp.R
import co.edu.ufps.miapp.controladores.TiendaAdapter
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tienda.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tienda : Fragment() {
    lateinit var contenedorTienda: RecyclerView
    lateinit var tiendaAdapter: TiendaAdapter
    // lateinit var registrarTienda: FloatingActionButton

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tienda, container, false)
        contenedorTienda = view.findViewById(R.id.contenedor_tienda)
        val linearLayout = LinearLayoutManager(context)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        contenedorTienda.layoutManager=linearLayout
        tiendaAdapter = TiendaAdapter(context,cargarDatosFireBase(),R.id.card)
        contenedorTienda.adapter=tiendaAdapter
        // registrarLibro = view.findViewById(R.id.registrar_libro)
        // registrarLibro.setOnClickListener(View.OnClickListener { agregarLibro() })
        return view
        return inflater.inflate(R.layout.fragment_tienda, container, false)
    }

    fun cargarDatos(): ArrayList<co.edu.ufps.miapp.modelo.Tienda> {
        val tiendas: ArrayList<co.edu.ufps.miapp.modelo.Tienda> = java.util.ArrayList()
        tiendas.add (
            co.edu.ufps.miapp.modelo.Tienda("13a",
                "Don Jose",
                "Viveres",
                "",
                "",
                "",
                ""))
        tiendas.add (co.edu.ufps.miapp.modelo.Tienda("13a",
            "Doña Naria",
            "Viveres",
            "",
            "",
            "",
            ""))
        return tiendas
    }

    fun cargarDatosFireBase(): ArrayList<co.edu.ufps.miapp.modelo.Tienda> {
        val tiendas= ArrayList<co.edu.ufps.miapp.modelo.Tienda>()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.reference
        // Read from the database
        myRef.child("tiendas").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (snapshot.exists()){
                    tiendas.clear()
                    for(data in snapshot.children){
                        val tienda=data.getValue(co.edu.ufps.miapp.modelo.Tienda::class.java)
                        tiendas.add(tienda as co.edu.ufps.miapp.modelo.Tienda)
                        tiendaAdapter.notifyDataSetChanged()
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Tienda", "Failed to read value.", error.toException())
            }

        })
        return tiendas;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tienda.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tienda().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}