package com.digruttola.firebasedatabase.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.digruttola.firebasedatabase.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var btGuardar : Button
    lateinit var btListado : Button
    lateinit var editName : EditText
    lateinit var editApellido : EditText
    lateinit var editEdad : EditText
    lateinit var editFechaNacimiento : EditText
    lateinit var editDNI : EditText
    lateinit var editEmail : EditText

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGuardar = findViewById(R.id.btGuardar)
        btListado = findViewById(R.id.btListaUsuario)
        editName  = findViewById(R.id.editNombre)
        editApellido = findViewById(R.id.editApellido)
        editEdad = findViewById(R.id.editEdad)
        editFechaNacimiento = findViewById(R.id.editFechaNacimiento)
        editDNI = findViewById(R.id.editDNI_main)
        editEmail = findViewById(R.id.editEmail)

        btGuardar.setOnClickListener {
            if( verificar() ){

                var nombreString = editName.text.toString()
                var apellidoString  = editApellido.text.toString()

                //String to Int
                var edadString = editEdad.text.toString()
                var edadInt = Integer.parseInt(edadString)

                //String to Date
                var fechaNacimientoString = editFechaNacimiento.text.toString()
                var fechaNacimientoDate : Date = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimientoString)

                //String to Int
                var dniString = editDNI.text.toString()
                var dniInt = Integer.parseInt(dniString)

                var emailString = editEmail.text.toString()

                val myMap = hashMapOf(
                    "nombre" to nombreString,
                    "apellido" to apellidoString,
                    "edad" to edadInt,
                    "fecha" to fechaNacimientoDate,
                    "dni" to dniInt,
                    "email" to emailString
                )

                var cloud : FirebaseFirestore = FirebaseFirestore.getInstance()
                cloud.collection("User").document().set(myMap).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Se Agrego con exito",Toast.LENGTH_SHORT).show()
                        clear()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_SHORT).show()
                    Log.d("TAG", it.message.toString())
                }

            }else
                Toast.makeText(this,"Campos Vacios",Toast.LENGTH_SHORT).show()

        }

        editFechaNacimiento.setOnClickListener{
            var calendar = Calendar.getInstance()
            var dia = calendar.get(Calendar.DAY_OF_MONTH)
            var mes = calendar.get(Calendar.MONTH)
            var anio = calendar.get(Calendar.YEAR)

            var picker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                editFechaNacimiento.setText("$dayOfMonth/${monthOfYear+1}/$year")
            },anio,mes,dia)

            picker.show()

        }

        btListado.setOnClickListener {
            var i  = Intent(this , ActivityListado::class.java)
            startActivity(i)
        }
    }

    fun verificar() : Boolean {
        return  !editName.text.isEmpty() &&
                !editApellido.text.isEmpty() &&
                !editEdad.text.isEmpty() &&
                !editDNI.text.isEmpty() &&
                !editFechaNacimiento.text.isEmpty() &&
                !editEmail.text.isEmpty()
    }
    fun clear(){
        editName.setText("")
        editApellido.setText("")
        editEdad.setText("")
        editDNI.setText("")
        editFechaNacimiento.setText("")
        editEmail.setText("")
    }
}

