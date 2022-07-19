package com.digruttola.firebasedatabase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digruttola.firebasedatabase.ListadoRecyclerAdapter
import com.digruttola.firebasedatabase.R
import com.digruttola.firebasedatabase.User
import com.google.firebase.firestore.FirebaseFirestore

class ActivityListado : AppCompatActivity() , ListadoRecyclerAdapter.ViewHolder.OnNoteListener{

    lateinit var recyclerView : RecyclerView

    private var users : ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

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