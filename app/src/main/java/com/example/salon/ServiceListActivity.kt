package com.example.salon

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

// ---------- DATA ----------
data class Service(
    val name: String,
    val price: String,
    val duration: String,
)

val demoServices = listOf(
    Service("Haircut", "$20", "30 min"),
    Service("Hair Coloring", "$45", "90 min"),
    Service("Blow Dry", "$15", "20 min"),
    Service("Nails", "$10", "25 min"),
)

// ---------- ACTIVITY ----------
class ServiceListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ServiceListScreen(
                onServiceSelected = { service ->
                    showDateTimePicker(service)
                },
                onBack = {
                    finish() // go back to home screen
                }
            )
        }
    }

    // ---------- DATE + TIME PICKER ----------
    private fun showDateTimePicker(service: Service) {
        val calendar = Calendar.getInstance()

        // Date picker
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)

                // Time picker
                val timePicker = TimePickerDialog(
                    this,
                    { _, hour, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)

                        val date = "$day/${month + 1}/$year"
                        val time = String.format("%02d:%02d", hour, minute)

                        Toast.makeText(
                            this,
                            "Booked ${service.name} on $date at $time",
                            Toast.LENGTH_LONG
                        ).show()

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )

                timePicker.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}

// ---------- UI COMPOSABLE ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    onServiceSelected: (Service) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Services") },
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
                .padding(16.dp)
        ) {

            LazyColumn {
                items(demoServices) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onServiceSelected(service) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = service.name, style = MaterialTheme.typography.titleLarge)
                            Text(text = service.price)
                            Text(text = service.duration)
                        }
                    }
                }
            }
        }
    }
}
