package com.example.salon

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

// -------------------- DATA --------------------
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

// -------------------- ACTIVITY --------------------
class ServiceListActivity : ComponentActivity() {

    // ViewModel for booking logic
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ServiceListScreen(
                onServiceSelected = { service ->
                    showDateTimePicker(service)
                },
                onBack = { finish() }
            )
        }
    }

    private fun showDateTimePicker(service: Service) {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)

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

                        // Save using ViewModel (clean code)
                        bookingViewModel.book(
                            serviceName = service.name,
                            date = date,
                            time = time
                        )

                        // Open confirmation screen
                        val intent = Intent(this, BookingConfirmationActivity::class.java).apply {
                            putExtra("service_name", service.name)
                            putExtra("date", date)
                            putExtra("time", time)
                        }
                        startActivity(intent)

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

// -------------------- UI SCREEN --------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    onServiceSelected: (Service) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose a Service") },
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

            // Header text
            Text(
                text = "Select what you’d like today",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tap a service to pick your date and time.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(demoServices) { service ->
                    ServiceCard(
                        service = service,
                        onClick = { onServiceSelected(service) }
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: Service,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Duration: ${service.duration}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                // Price badge
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small,
                    tonalElevation = 2.dp
                ) {
                    Text(
                        text = service.price,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap to choose time",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
