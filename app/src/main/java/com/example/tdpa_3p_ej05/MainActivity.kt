package com.example.tdpa_3p_ej05

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tdpa_3p_ej05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAlta.setOnClickListener {
            alta()
        }

        binding.btnbyCodigo.setOnClickListener {
            buscarByCodigo()
        }

        binding.btnBorrar.setOnClickListener {
            borrarArticulo()
        }

        binding.btnActualizar.setOnClickListener {
            modificarArticulo()
        }
    }

    fun alta() {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("codigo", binding.txtCodigo.text.toString())
        registro.put("descripcion", binding.txtDescripcion.text.toString())
        registro.put("precio", binding.txtPrecio.text.toString())
        bd.insert("articulos", null, registro)
        bd.close()
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
        Toast.makeText(this, "Se insertó correctamente el artículo", Toast.LENGTH_SHORT).show()
    }

    fun buscarByCodigo(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo=${binding.txtCodigo.text}", null)
        if(fila.moveToFirst()){
            binding.txtDescripcion.setText(fila.getString(0))
            binding.txtPrecio.setText(fila.getString(1))
        } else {
            Toast.makeText(this, "No hubo coincidencia con ese código", Toast.LENGTH_SHORT).show()
        }
    }

    fun buscarByDescripcion(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT codigo, precio FROM articulos WHERE codigo=${binding.txtDescripcion.text}", null)
        if(fila.moveToFirst()){
            binding.txtCodigo.setText(fila.getString(0))
            binding.txtPrecio.setText(fila.getString(1))
        } else {
            Toast.makeText(this, "No hubo coincidencia con ese código", Toast.LENGTH_SHORT).show()
        }
    }

    fun borrarArticulo(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("articulos", "codigo=${binding.txtCodigo.text}", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this, "El artículo se borró correctamente", Toast.LENGTH_SHORT).show()
            binding.txtCodigo.setText("")
            binding.txtDescripcion.setText("")
            binding.txtPrecio.setText("")
        } else {
            Toast.makeText(this, "No hubo coincidencia con ese código", Toast.LENGTH_SHORT).show()
        }
    }

    fun modificarArticulo(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("descripcion", binding.txtDescripcion.text.toString())
        registro.put("precio", binding.txtPrecio.text.toString())
        val cant = bd.update("articulos", registro, "codigo=${binding.txtCodigo.text}", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this, "El artículo se actualizó correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No hubo coincidencia con ese código", Toast.LENGTH_SHORT).show()
        }
    }
}