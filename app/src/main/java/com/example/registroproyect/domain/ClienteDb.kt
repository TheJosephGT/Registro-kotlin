package com.example.registroproyect.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registroproyect.data.local.dao.ClienteDao
import com.example.registroproyect.data.local.entities.Cliente

@Database(
    entities = [Cliente::class],
    version = 1
)
abstract class ClienteDb : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
}