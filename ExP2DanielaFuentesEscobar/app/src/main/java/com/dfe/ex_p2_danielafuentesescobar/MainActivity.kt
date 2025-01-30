package com.dfe.ex_p2_danielafuentesescobar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.dfe.ex_p2_danielafuentesescobar.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "medicionesdb"
            ).build()

            val medicionDao = db.medicionDao()
            val m = Medicion("123.456", "2024-11-11", TipoServicio.AGUA)
            medicionDao.insert(m)
        }

        enableEdgeToEdge()
        setContent {
            AppMediciones()
            }
        }
    }

enum class TipoServicio(val imageRes: Int) {
    AGUA(R.drawable.agua),
    LUZ(R.drawable.luz),
    GAS(R.drawable.gas);
}


    data class Medicion(
        val Medicion: String,
        val Fecha: String,
        val Servicio: TipoServicio
    )

    @Preview(showSystemUi = true, locale = "en")
    @Composable
    fun AppMediciones(

        NavController: NavHostController = rememberNavController()
    ) {
        NavHost(
            navController = NavController,
            startDestination = "inicio"
        ) {
            composable("inicio") {
                PantallaInicioUI(
                    onClick = { NavController.navigate("Formulario Medicion") }
                )
            }
            composable("Formulario Medicion") {
                PageFormMedicionUI(
                    navController = NavController,
                    onClick = { NavController.popBackStack() }
                )

            }
        }
    }

    @Composable
    fun PantallaInicioUI(
        onClick: () -> Unit = {}
    ) {
        val contexto = LocalContext.current

        val listadoMediciones = listOf<Medicion>(
            Medicion(Medicion = "143.434", Fecha = "2025-01-29", Servicio = TipoServicio.AGUA),
            Medicion(Medicion = "2.345.245", Fecha = "2025-01-30", Servicio = TipoServicio.LUZ),
            Medicion(Medicion = "3.235.245", Fecha = "2025-01-31", Servicio = TipoServicio.GAS)
        )

        Column {
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = contexto.getString(R.string.app_name),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp
                )

            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 80.dp)
            ) {
                items(listadoMediciones) { medicion ->
                    Column(modifier = Modifier.padding(bottom = 16.dp))
                    {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = medicion.Servicio.imageRes),
                                contentDescription = medicion.Servicio.name,
                                modifier = Modifier.size(24.dp)  // Ajusta el tamaño de la imagen
                            )
                            Spacer(modifier = Modifier.width(10.dp)) // Espacio entre la imagen y el texto

                            // Información de la medición
                            Column {
                                Text(medicion.Servicio.name, fontWeight = FontWeight.Bold)
                                Text(medicion.Medicion)
                                Text(medicion.Fecha)

                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

                item {
                    Button(
                        onClick = { onClick() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Agregar Nueva Medicion"
                        )
                        Text(contexto.getString(R.string.btn_txt_guardarMedicion))
                    }
                }
            }
        }
    }

   @Composable
   fun PageFormMedicionUI(
       navController: NavHostController,
       onClick: () -> Unit = {}
   ) {
       var medicion by remember { mutableStateOf("") }
       var fecha by remember { mutableStateOf("") }
       var selectedServicios by remember { mutableStateOf(setOf<TipoServicio>()) }

       // Layout para el formulario
       Column(modifier = Modifier.padding(70.dp)) {
           Text(
               text = "Agregar Medición",
               fontSize = 24.sp,
               modifier = Modifier.padding(bottom = 16.dp)
           )

           // Campo para la medición
           TextField(
               value = medicion,
               onValueChange = { medicion = it },
               label = { Text("Medición") },
               modifier = Modifier.fillMaxWidth()
           )

           Spacer(modifier = Modifier.height(16.dp))

           // Campo para la fecha
           TextField(
               value = fecha,
               onValueChange = { fecha = it },
               label = { Text("Fecha (yyyy-mm-dd)") },
               modifier = Modifier.fillMaxWidth()
           )

           Spacer(modifier = Modifier.height(16.dp))

           // Checkboxes para seleccionar el tipo de servicio
           Text(
               text = "Seleccione un servicio:",
               fontSize = 18.sp,
               modifier = Modifier.padding(bottom = 8.dp)
           )

           // Checkbox para AGUA
           Row(verticalAlignment = Alignment.CenterVertically) {
               Checkbox(
                   checked = selectedServicios.contains(TipoServicio.AGUA),
                   onCheckedChange = { isChecked ->
                       selectedServicios = if (isChecked) {
                           selectedServicios + TipoServicio.AGUA
                       } else {
                           selectedServicios - TipoServicio.AGUA
                       }
                   }
               )
               Text(text = "Agua", modifier = Modifier.padding(start = 8.dp))
           }

           // Checkbox para LUZ
           Row(verticalAlignment = Alignment.CenterVertically) {
               Checkbox(
                   checked = selectedServicios.contains(TipoServicio.LUZ),
                   onCheckedChange = { isChecked ->
                       selectedServicios = if (isChecked) {
                           selectedServicios + TipoServicio.LUZ
                       } else {
                           selectedServicios - TipoServicio.LUZ
                       }
                   }
               )
               Text(text = "Luz", modifier = Modifier.padding(start = 8.dp))
           }

           // Checkbox para GAS
           Row(verticalAlignment = Alignment.CenterVertically) {
               Checkbox(
                   checked = selectedServicios.contains(TipoServicio.GAS),
                   onCheckedChange = { isChecked ->
                       selectedServicios = if (isChecked) {
                           selectedServicios + TipoServicio.GAS
                       } else {
                           selectedServicios - TipoServicio.GAS
                       }
                   }
               )
               Text(text = "Gas", modifier = Modifier.padding(start = 8.dp))
           }

           Spacer(modifier = Modifier.height(16.dp))

           // Botón para guardar la medición y regresar a la pantalla de inicio
           Button(
               onClick = {
                   if (medicion.isNotBlank() && fecha.isNotBlank()) {
                       // Aquí podrías agregar la lógica para guardar la medición en la base de datos o manejarla como desees
                       println("Medición guardada: $medicion, Fecha: $fecha, Servicios: $selectedServicios")

                       // Navegar hacia la pantalla de inicio después de guardar la medición
                       navController.popBackStack() // Regresa a la pantalla de inicio
                   } else {
                       println("Por favor complete todos los campos.")
                   }
               },
               modifier = Modifier.fillMaxWidth()
           ) {
               Text("Guardar Nueva Medición")
           }
       }
   }



