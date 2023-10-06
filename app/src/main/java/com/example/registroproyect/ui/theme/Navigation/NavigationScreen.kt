package com.example.registroproyect.ui.theme.Navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registroproyect.R
import com.example.registroproyect.ui.theme.ClientesUI.Consult
import com.example.registroproyect.ui.theme.ClientesUI.ScreenPrincipal
import com.example.registroproyect.ui.theme.ClientesUI.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, appItems = Destination.toList) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavigation(navController = navController)
            }
        }
    )
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: ClienteViewModel = hiltViewModel()) {
    NavHost(navController, startDestination = Destination.RegistroClientes.route) {
        composable(Destination.RegistroClientes.route) {
            ScreenPrincipal()
        }
        composable(Destination.ConsultaClientes.route) {
            val clientes by viewModel.clientes.collectAsStateWithLifecycle()
            Consult(clientes = clientes)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, appItems: List<Destination>) {

    BottomAppBar(
        containerColor = colorResource(id = R.color.purple_700),
        contentColor = Color.White
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            items(appItems) { appitem ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate(appitem.route)
                    }.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = appitem.icon,
                        contentDescription = appitem.title
                    )
                    Text(
                        text = appitem.title,
                        color = Color.White
                    )
                }
            }
        }
    }

}