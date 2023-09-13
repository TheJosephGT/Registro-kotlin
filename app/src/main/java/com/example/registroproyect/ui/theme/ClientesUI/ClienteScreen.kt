package com.example.registroproyect.ui.theme.ClientesUI

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registroproyect.viewModel.ClienteViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ScreenPrincipal(viewModel: ClienteViewModel = hiltViewModel()) {
    val clientes by viewModel.clientes.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Cliente guardado",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Clientes") },
                actions = {
                    IconButton(onClick = { viewModel.limpiar() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh, contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                Text(text = "Cliente detalles", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = viewModel.Nombre,
                    onValueChange = { viewModel.Nombre = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Nombre") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.NombreError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.NombreError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.Telefono,
                    onValueChange = { viewModel.Telefono = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Telefono") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.TelefonoError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.TelefonoError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.Celular,
                    onValueChange = { viewModel.Celular = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Celular") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.CelularError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.CelularError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.Email,
                    onValueChange = { viewModel.Email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Email") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.EmailError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.EmailError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.Direccion,
                    onValueChange = { viewModel.Direccion = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Dirección") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.DireccionError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.DireccionError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.FechaNacimiento,
                    onValueChange = { viewModel.FechaNacimiento = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "FechaNacimiento") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Icon")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.FechaNacimientoError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.FechaNacimientoError) Color.Gray else Color.Red
                    )
                )
                OutlinedTextField(
                    value = viewModel.Ocupacion,
                    onValueChange = { viewModel.Ocupacion = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Ocupacion") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.OcupacionError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.OcupacionError) Color.Gray else Color.Red
                    )
                )
                OutlinedButton(onClick = {
                    keyboardController?.hide()
                    if (viewModel.Validar()) {
                        viewModel.saveCliente()
                        viewModel.setMessageShown()
                    }
                }, modifier = Modifier.fillMaxWidth())
                {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
                    Text(text = "Guardar")
                }
            }

            Text(text = "Lista de clientes", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(clientes) { Cliente ->
                    Text(text = Cliente.Nombre)
                }
            }
        }
    }
}