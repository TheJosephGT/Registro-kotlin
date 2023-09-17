package com.example.registroproyect.ui.theme.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String, val icon: ImageVector, val title: String) {
    object RegistroClientes : Destination(
        route = "Registro", icon = Icons.Filled.Person,
        title = "Registro"
    )

    object ConsultaClientes : Destination(
        route = "Consulta", icon = Icons.Filled.Email,
        title = "Consulta"
    )

    companion object {
        val toList = listOf(RegistroClientes, ConsultaClientes)
    }
}