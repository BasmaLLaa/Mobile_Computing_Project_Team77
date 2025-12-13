package com.example.salon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class BookingConfirmationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceName = intent.getStringExtra("service_name") ?: "Unknown service"
        val date = intent.getStringExtra("date") ?: "-"
        val time = intent.getStringExtra("time") ?: "-"

        setContent {
            BookingConfirmationScreen(
                serviceName = serviceName,
                date = date,
                time = time,
                onLocationClick = { openMaps() },
                onBackHomeClick = { goHome() },
                onBack = { finish() }
            )
        }
    }

    private fun openMaps() {
        val latitude = 30.0444
        val longitude = 31.2357
        val label = "My Salon"

        val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($label)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingConfirmationScreen(
    serviceName: String,
    date: String,
    time: String,
    onLocationClick: () -> Unit,
    onBackHomeClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Confirmed") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Your Booking",
                style = MaterialTheme.typography.headlineMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Service: $serviceName", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Date: $date")
                    Text(text = "Time: $time")
                }
            }

            Button(
                onClick = onLocationClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Location")
            }

            Button(
                onClick = onBackHomeClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}
