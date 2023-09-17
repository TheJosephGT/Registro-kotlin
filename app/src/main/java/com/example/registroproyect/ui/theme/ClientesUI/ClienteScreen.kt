package com.example.registroproyect.ui.theme.ClientesUI

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registroproyect.viewModel.ClienteViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.geometry.Size

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.registroproyect.data.local.entities.Cliente
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ScreenPrincipal(viewModel: ClienteViewModel = hiltViewModel()) {
    val clientes by viewModel.clientes.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RefreshAppBar(title = "Clientes") {
                viewModel.limpiar()
                selectedItem = ""
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Text(text = "Cliente detalles", style = MaterialTheme.typography.titleMedium)

            CustomOutlinedTextField(
                value = viewModel.Nombre,
                onValueChange = { viewModel.Nombre = it },
                label = "Nombre",
                isError = viewModel.NombreError
            )
            CustomOutlinedTextField(
                value = viewModel.Telefono,
                onValueChange = { viewModel.Telefono = it },
                label = "Telefono",
                isError = viewModel.TelefonoError
            )
            CustomOutlinedTextField(
                value = viewModel.Celular,
                onValueChange = { viewModel.Celular = it },
                label = "Celular",
                isError = viewModel.CelularError
            )
            CustomOutlinedTextField(
                value = viewModel.Email,
                onValueChange = { viewModel.Email = it },
                label = "Email",
                isError = viewModel.EmailError
            )
            CustomOutlinedTextField(
                value = viewModel.Direccion,
                onValueChange = { viewModel.Direccion = it },
                label = "Dirección",
                isError = viewModel.DireccionError
            )
            val context = LocalContext.current
            DateText(viewModel = viewModel, context = context)
            Column {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange = {
                        selectedItem = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = { Text("Ocupacion") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (viewModel.OcupacionError) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (viewModel.OcupacionError) Color.Gray else Color.Red
                    ),
                    trailingIcon = {
                        Icon(icon, "", Modifier.clickable { expanded = !expanded })
                    },
                    readOnly = true
                )
                DropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(
                        with(LocalDensity.current) { textFiledSize.width.toDp() }
                    )) {
                    viewModel.OcupacionList.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            selectedItem = label
                            expanded = false
                            viewModel.Ocupacion = selectedItem
                        })
                    }
                }
            }
            OutlinedButton(onClick = {
                keyboardController?.hide()
                if (viewModel.Validar()) {
                    viewModel.saveCliente()
                    viewModel.setMessageShown()
                    selectedItem = ""
                }
            }, modifier = Modifier.fillMaxWidth())
            {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
                Text(text = "Guardar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshAppBar(
    title: String,
    onRefreshClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { onRefreshClick() }) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Refresh"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isError) Color.Gray else Color.Red
        )
    )
}

@Composable
fun Consult(clientes: List<Cliente>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Lista de clientes", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(clientes) { cliente ->
                ClienteItem(cliente)
            }
        }
    }
}

@Composable
fun ClienteItem(cliente: Cliente, viewModel: ClienteViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = cliente.Nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.Telefono, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.Celular, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.Email, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.Direccion, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.FechaNacimiento, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.Ocupacion, style = MaterialTheme.typography.titleMedium)

            Button(
                onClick = {
                    viewModel.delteCliente(cliente)
                }
            ) {
                Text(text = "Eliminar")
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DateText(
    viewModel: ClienteViewModel, context: Context,
) {
    var PickerVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val dia: Int
    val mes: Int
    val anio: Int
    val calendario = Calendar.getInstance()
    calendario.time = Date()
    dia = calendario.get(Calendar.DAY_OF_MONTH)
    anio = calendario.get(Calendar.YEAR)
    mes = calendario.get(Calendar.MONTH)

    OutlinedTextField(
        value = viewModel.FechaNacimiento,
        onValueChange = { viewModel.FechaNacimiento = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Fecha de Nacimiento") },
        singleLine = true,
        readOnly = true,
        leadingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                PickerVisible = true
            }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Date Icon",
                    tint = Color.Gray
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (viewModel.FechaNacimientoError) Color.Gray else Color.Red,
            unfocusedBorderColor = if (viewModel.FechaNacimientoError) Color.Gray else Color.Red
        )
    )
    if (PickerVisible) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, anio: Int, mes: Int, dia: Int ->
                viewModel.FechaNacimiento = "$dia/$mes/$anio"
                PickerVisible = false
            },
            anio, mes, dia
        )
        DisposableEffect(Unit) {
            datePickerDialog.show()
            onDispose {
                datePickerDialog.dismiss()
            }
        }
    }
}
