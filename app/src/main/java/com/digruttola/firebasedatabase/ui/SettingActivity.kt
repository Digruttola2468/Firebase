package com.digruttola.firebasedatabase.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.digruttola.firebasedatabase.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity() {

    lateinit var btGuardar : Button
    lateinit var btEliminar : Button
    lateinit var btVolver : Button
    lateinit var editName : EditText
    lateinit var editApellido : EditText
    lateinit var editEdad : EditText
    lateinit var editFechaNacimiento : EditText
    lateinit var editDNI : EditText
    lateinit var editEmail : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btGuardar = findViewById(R.id.btGuardar_setting)
        btEliminar = findViewById(R.id.btEliminar_setting)
        btVolver = findViewById(R.id.btVolver_setting)

        editName  = findViewById(R.id.editNombre_setting)
        editApellido = findViewById(R.id.editApellido_setting)
        editEdad = findViewById(R.id.editEdad_setting)
        editFechaNacimiento = findViewById(R.id.editFechaNacimiento_setting)
        editDNI = findViewById(R.id.editDNI_main_setting)
        editEmail = findViewById(R.id.editEmail_setting)


        val bundle = intent.extras
        var id = bundle?.get("id")
        var nombre = bundle?.get("nombre")
        var apellido = bundle?.get("apellido")
        var edad = bundle?.get("edad")
        var dni = bundle?.get("dni")
        var email = bundle?.get("email")

        //Date to String
        var fechaNacimiento = bundle?.get("fechaNacimiento")
        var fechaNacimientoString = SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)

        editName.setText(nombre.toString())
        editApellido.setText(apellido.toString())
        editEdad.setText(edad.toString())
        editFechaNacimiento.setText(apellido.toString())
        editDNI.setText(dni.toString())
        editEmail.setText(email.toString())
        editFechaNacimiento.setText(fechaNacimientoString)


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

        btVolver.setOnClickListener{
            startActivity(Intent(this,ActivityListado::class.java))
            finish()
        }
        btGuardar.setOnClickListener{

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

            val myMap : Map<String,Any> = hashMapOf(
                "nombre" to nombreString,
                "apellido" to apellidoString,
                "edad" to edadInt,
                "fecha" to fechaNacimientoDate,
                "dni" to dniInt,
                "email" to emailString
            )

            var db = FirebaseFirestore.getInstance()
            db.collection("User").document(id.toString()).update(myMap).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Se actualizo correctamente",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ActivityListado::class.java))
                    finish()
                }
            }
        }
        btEliminar.setOnClickListener{
            var db = FirebaseFirestore.getInstance()
            db.collection("User").document(id.toString()).delete().addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Se elimino correctamente",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ActivityListado::class.java))
                    finish()
                }
            }
        }
    }

}