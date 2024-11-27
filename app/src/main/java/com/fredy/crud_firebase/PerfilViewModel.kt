package com.fredy.crud_firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PerfilViewModel : ViewModel() {

    private val db = Firebase.firestore

    private var _listaPerfiles = MutableLiveData<List<Perfil>>(emptyList())
    val listaPerfiles: LiveData<List<Perfil>> = _listaPerfiles

    init {
        obtenerPerfiles()
    }

    fun obtenerPerfiles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("perfiles").get().await()
                val perfiles = resultado.documents.mapNotNull { doc ->
                    doc.toObject(Perfil::class.java)?.apply { id = doc.id }
                }
                _listaPerfiles.postValue(perfiles)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarPerfil(perfil: Perfil) {
        perfil.id = UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("perfiles").document(perfil.id).set(perfil).await()
                obtenerPerfiles()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun actualizarPerfil(perfil: Perfil) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("perfiles").document(perfil.id).update(perfil.toMap()).await()
                obtenerPerfiles()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun borrarPerfil(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("perfiles").document(id).delete().await()
                obtenerPerfiles()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}