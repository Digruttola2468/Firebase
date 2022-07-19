package com.digruttola.firebasedatabase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digruttola.firebasedatabase.ListadoRecyclerAdapter
import com.digruttola.firebasedatabase.R
import com.digruttola.firebasedatabase.User
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ActivityListado : AppCompatActivity() , ListadoRecyclerAdapter.ViewHolder.OnNoteListener{

    lateinit var recyclerView : RecyclerView

    private var users : ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var db = FirebaseFirestore.getInstance()
        /*db.collection("User")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        users.add(
                            User(
                                document.id,
                                document.getString("nombre")!!,
                                document.getString("apellido")!!,
                                Integer.parseInt(document.get("edad").toString()!!),
                                document.getDate("fecha")!!,
                                document.getLong("dni")!!,
                                document.getString("email")!!
                            )
                        )
                    }
                    users.toArray().map { t ->  Log.d("TAG",t.toString()) }

                    recyclerView.adapter = ListadoRecyclerAdapter(users, onNoteListener = this)

                } else Log.w("", "Error getting documents.", task.exception)
            }.addOnFailureListener {
                Log.w("TAG",it.message.toString())
            }*/



        db.collection("User").addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            if(firebaseFirestoreException != null){
                return@addSnapshotListener
            }
            for(document in querySnapshot!!.documentChanges){
                if (document.type == DocumentChange.Type.ADDED) {
                    var id = document.document.id
                    var name = document.document.get("nombre")
                    var apellido = document.document.get("apellido")
                    var edad = document.document.get("edad")
                    var dni = document.document.get("dni")
                    var fechaNacimiento = document.document.getDate("fecha")
                    var email = document.document.get("email")

                    Log.d("REALTIME", "Nombre: $name \nApellido: $apellido\nEdad: $edad\nDni: $dni\nFecha: $fechaNacimiento\nEmail: $email")

                    users.add(User(
                        id = id.toString(),
                        name = name.toString(),
                        apellido = apellido.toString(),
                        edad = Integer.parseInt(edad.toString()),
                        fechaNacimiento = fechaNacimiento!!,
                        dni = dni as Long,
                        email = email.toString()
                    ))

                    recyclerView.adapter = ListadoRecyclerAdapter(users, onNoteListener = this)

                    /*RecyclerViewTurnosAdapter adapter = new RecyclerViewTurnosAdapter(
                        new Turno (documents.getDocument().getId(), nombre, hora, fechas
                    ));
                    recyclerView.setAdapter(adapter);*/
                }
                if(document.type == DocumentChange.Type.MODIFIED || document.type == DocumentChange.Type.REMOVED){
                    users.clear()
                    var db = FirebaseFirestore.getInstance()
                    db.collection("User")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    users.add(
                                        User(
                                            document.id,
                                            document.getString("nombre")!!,
                                            document.getString("apellido")!!,
                                            Integer.parseInt(document.get("edad").toString()!!),
                                            document.getDate("fecha")!!,
                                            document.getLong("dni")!!,
                                            document.getString("email")!!
                                        )
                                    )
                                }
                                users.toArray().map { t ->  Log.d("TAG",t.toString()) }

                                recyclerView.adapter = ListadoRecyclerAdapter(users, onNoteListener = this)

                            } else Log.w("", "Error getting documents.", task.exception)
                        }.addOnFailureListener {
                            Log.w("TAG",it.message.toString())
                        }
                }
            }
        }
    }

    override fun onNoteClick(position: Int) {
        Log.d("TAG","onNoteListener: " + users.elementAt(position).id)

        var i = Intent(this,SettingActivity::class.java).apply {
            putExtra("id", users.elementAt(position).id)
            putExtra("nombre", users.elementAt(position).name)
            putExtra("apellido", users.elementAt(position).apellido)
            putExtra("edad", users.elementAt(position).edad)
            putExtra("dni", users.elementAt(position).dni)
            putExtra("email", users.elementAt(position).email)
            putExtra("fechaNacimiento",users.elementAt(position).fechaNacimiento)
        }
        startActivity(i)
        finish()
    }

}