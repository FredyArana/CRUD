package com.fredy.crud_firebase

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fredy.crud_firebase.databinding.ItemPerfilBinding

class PerfilAdapter(
    private val listaPerfiles: List<Perfil>,
    private val onBorrarClick: (String) -> Unit,
    private val onEditarClick: (Perfil) -> Unit
) : RecyclerView.Adapter<PerfilAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPerfilBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(perfil: Perfil, onBorrarClick: (String) -> Unit, onEditarClick: (Perfil) -> Unit) {
            binding.tvNombre.text = perfil.nombre
            binding.tvCorreo.text = perfil.correo

            binding.ibtnBorrar.setOnClickListener {
                onBorrarClick(perfil.id)
            }

            binding.cvPerfil.setOnClickListener {
                onEditarClick(perfil)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPerfilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perfil = listaPerfiles[position]
        holder.bind(perfil, onBorrarClick, onEditarClick)
    }

    override fun getItemCount(): Int = listaPerfiles.size
}