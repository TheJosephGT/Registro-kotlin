package com.example.registroproyect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.registroproyect.data.local.ClienteDb
import com.example.registroproyect.ui.theme.RegistroProyectTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.registroproyect.ui.theme.ClientesUI.ScreenPrincipal
import com.example.registroproyect.ui.theme.Navigation.AppScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var clienteDb: ClienteDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroProyectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   ScreenPrincipal()
                    AppScreen()
                }
            }
        }
    }
}