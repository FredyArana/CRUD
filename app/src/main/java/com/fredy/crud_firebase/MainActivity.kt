package com.fredy.crud_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.fredy.crud_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PerfilAdapter
    private lateinit var viewModel: PerfilViewModel

    var perfilEdit = Perfil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]

        viewModel.listaPerfiles.observe(this) { perfiles ->
            setupRecyclerView(perfiles)
        }

        binding.btnAgregarPerfil.setOnClickListener {
            val perfil = Perfil(
                nombre = binding.etNombre.text.toString(),
                correo = binding.etCorreo.text.toString()
            )
            viewModel.agregarPerfil(perfil)

            binding.etNombre.setText("")
            binding.etCorreo.setText("")
        }

        binding.btnActualizarPerfil.setOnClickListener {
            perfilEdit.nombre = binding.etNombre.text.toString()
            perfilEdit.correo = binding.etCorreo.text.toString()

            viewModel.actualizarPerfil(perfilEdit)

            adapter.notifyDataSetChanged()

            binding.etNombre.setText("")
            binding.etCorreo.setText("")
        }
    }

    private fun setupRecyclerView(listaPerfiles: List<Perfil>) {
        adapter = PerfilAdapter(listaPerfiles, ::borrarPerfil, ::editarPerfil)
        binding.rvPerfiles.adapter = adapter
    }

    private fun borrarPerfil(id: String) {
        viewModel.borrarPerfil(id)
    }

    private fun editarPerfil(perfil: Perfil) {
        perfilEdit = perfil

        binding.etNombre.setText(perfilEdit.nombre)
        binding.etCorreo.setText(perfilEdit.correo)
    }
}