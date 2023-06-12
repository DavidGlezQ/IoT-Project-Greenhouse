package com.davidglez.greenhouse

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidglez.greenhouse.ui.theme.GreenhouseTheme

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

@Composable
fun Sensors() {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "Invernadero",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp), textAlign = TextAlign.Center
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
                Text(text = "Humedad de mi invernadero: ")
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
                Text(text = "Calidad CO2: ")
            }
        }
        Button(
            onClick = { },
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