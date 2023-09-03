package com.example.registroproyect.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clientes")
data class Cliente (
    @PrimaryKey
    val ClienteId: Int?=null,
    var Nombre: String = ""
)