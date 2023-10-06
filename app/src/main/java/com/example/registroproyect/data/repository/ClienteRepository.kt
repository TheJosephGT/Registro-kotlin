package com.example.registroproyect.data.repository

import com.example.registroproyect.data.local.dao.ClienteDao
import com.example.registroproyect.data.local.entities.Cliente
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val clienteDao: ClienteDao) {
    suspend fun save(cliente: Cliente) = clienteDao.save(cliente)
    suspend fun delete(cliente: Cliente) = clienteDao.delete(cliente)
    suspend fun find(Id: Int) = clienteDao.find(Id)
    fun getAll() = clienteDao.getAll()

}