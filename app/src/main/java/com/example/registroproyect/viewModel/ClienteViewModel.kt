package com.example.registroproyect.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroproyect.data.local.entities.Cliente
import com.example.registroproyect.domain.ClienteDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteDb: ClienteDb,
) : ViewModel() {
    var Nombre by mutableStateOf("")
    var Telefono by mutableStateOf("")
    var Email by mutableStateOf("")
    var Direccion by mutableStateOf("")
    var Celular by mutableStateOf("")
    var FechaNacimiento by mutableStateOf("")
    var Ocupacion by mutableStateOf("")
    val OcupacionList = listOf("Opcion 1", "Opcion 2", "Opcion 3")

    var NombreError by mutableStateOf(true)
    var TelefonoError by mutableStateOf(true)
    var EmailError by mutableStateOf(true)
    var DireccionError by mutableStateOf(true)
    var CelularError by mutableStateOf(true)
    var FechaNacimientoError by mutableStateOf(true)
    var OcupacionError by mutableStateOf(true)



    fun Validar(): Boolean {

        NombreError = Nombre.isNotEmpty()
        TelefonoError = Telefono.isNotEmpty()
        EmailError = Email.isNotEmpty()
        DireccionError = Direccion.isNotEmpty()
        CelularError = Celular.isNotEmpty()
        FechaNacimientoError = FechaNacimiento.isNotEmpty()
        OcupacionError = Ocupacion.isNotEmpty()

        return !(Nombre == "" || Telefono == "" || Email == "" || Direccion == "" || Celular == "" || FechaNacimiento == "" || Ocupacion == "")

    }

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val clientes: StateFlow<List<Cliente>> = clienteDb.clienteDao().getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun saveCliente() {
        viewModelScope.launch {
            val cliente = Cliente(
                Nombre = Nombre,
                Telefono = Telefono,
                Email = Email,
                Direccion = Direccion,
                Celular = Celular,
                FechaNacimiento = FechaNacimiento,
                Ocupacion = Ocupacion
            )
            clienteDb.clienteDao().save(cliente)
            limpiar()
        }
    }

    fun limpiar() {
        Nombre = ""
        Telefono = ""
        Email = ""
        Direccion = ""
        Celular = ""
        FechaNacimiento = ""
        Ocupacion = ""
    }


}