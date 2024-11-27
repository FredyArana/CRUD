package com.fredy.crud_firebase

data class Perfil(
    var id: String = "",
    var nombre: String = "",
    var correo: String = ""
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "nombre" to nombre,
            "correo" to correo
        )
    }
}