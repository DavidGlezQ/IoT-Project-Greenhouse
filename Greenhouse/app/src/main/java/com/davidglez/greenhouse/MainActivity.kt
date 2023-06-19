package com.davidglez.greenhouse

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidglez.greenhouse.ui.theme.GreenhouseTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Sensors()
                }
            }
        }
    }
}

//Firebase
@Composable
fun readFirebaseValue(ref: DatabaseReference): State<Long> {
    val variableValue = remember { mutableStateOf(0L) }

    DisposableEffect(ref) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Long::class.java)
                value?.let {
                    variableValue.value = it
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de lectura
            }
        }

        ref.addValueEventListener(valueEventListener)

        onDispose {
            ref.removeEventListener(valueEventListener)
        }
    }

    return variableValue
}

@Composable
fun MyApp() {
    val database = Firebase.database
    val ref = database.getReference("CO2SensorValue")
    val variableValue by readFirebaseValue(ref)

    // Composición de la interfaz de usuario
    Column {
        Text(text = "Valor de la variable: $variableValue")
    }
}


@Composable
fun Sensors() {
    val activity = LocalContext.current as Activity
    val database = Firebase.database
    val refCO2 = database.getReference("CO2SensorValue")
    val refHumidity = database.getReference("HW-080HumiditySensorValue")
    val variableValueCO2 by readFirebaseValue(refCO2)
    val variableValueHumidity by readFirebaseValue(refHumidity)
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Invernadero",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(Modifier.padding(16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_temperature),
                    contentDescription = "temperature", Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = "Temperatura de mi invernadero: ")
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(Modifier.padding(16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_humidity),
                    contentDescription = "humidity", Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = "Humedad de mi invernadero: %$variableValueHumidity")
            }
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(Modifier.padding(16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_co2),
                    contentDescription = "quality c02", Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = "Calidad CO2: $variableValueCO2 ppm")
            }
        }
        Button(
            onClick = { activity.finish() },
            Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Salir")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    GreenhouseTheme {
        Sensors()
    }
}