package com.digruttola.firebasedatabase

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListadoRecyclerAdapter(listUserContructor: ArrayList<User>,onNoteListener : ViewHolder.OnNoteListener) :
    RecyclerView.Adapter<ListadoRecyclerAdapter.ViewHolder>() {

    private var listUser : ArrayList<User> = ArrayList()
    private var  mOnNodeListener : ViewHolder.OnNoteListener = onNoteListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_listado, null, false)
        return ViewHolder(view, mOnNodeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.asignarNombre(listUser.elementAt(position).name,listUser.elementAt(position).apellido)
        holder.asignarEdad(listUser.elementAt(position).edad)
        holder.asignarFecha(listUser.elementAt(position).fechaNacimiento)
        holder.asignarDni(listUser.elementAt(position).dni)
        holder.asignarEmail(listUser.elementAt(position).email)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ViewHolder(itemView: View, onNodeListener : OnNoteListener) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        private var txtNombre : TextView = itemView.findViewById(R.id.txtUser_item_listado)
        private var txtEdad : TextView = itemView.findViewById(R.id.txtEdad_item_listado)
        private var txtDni : TextView = itemView.findViewById(R.id.txtDni_item_listado)
        private var txtEmail : TextView = itemView.findViewById(R.id.txtEmail_item_listado)
        private var txtFecha : TextView = itemView.findViewById(R.id.txtFecha_item_listado)
        var onNoteLister : OnNoteListener = onNodeListener

        fun asignarNombre(nombre : String,apellido : String){
            txtNombre.text = "$nombre $apellido"
        }
        fun asignarEdad(edad : Int){
            txtEdad.text = edad.toString()
        }
        fun asignarDni(dni : Long){
            txtDni.text = dni.toString()
        }
        fun asignarEmail(email : String){
            txtEmail.text = email
        }
        fun asignarFecha(fecha : Date){
            txtFecha.text = SimpleDateFormat("dd/MM/yyyy").format(fecha)
        }

        init {
            txtNombre = itemView.findViewById(R.id.txtUser_item_listado)
            txtEdad = itemView.findViewById(R.id.txtEdad_item_listado)
            txtDni  = itemView.findViewById(R.id.txtDni_item_listado)
            txtEmail  = itemView.findViewById(R.id.txtEmail_item_listado)
            txtFecha  = itemView.findViewById(R.id.txtFecha_item_listado)

            itemView.setOnClickListener(this)
        }

        interface OnNoteListener{
            fun onNoteClick(position : Int)
        }

        override fun onClick(p0: View?) {
            onNoteLister.onNoteClick(adapterPosition)
        }
    }
    init {
        this.listUser = listUserContructor
    }
}