package com.example.salon

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

class BookingListActivity : ComponentActivity() {

    // ViewModel (clean code / MVVM)
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Convert Flow<List<Appointment>> to Compose State
            val appointments by bookingViewModel.appointments.collectAsState(initial = emptyList())

            BookingListScreen(
                appointments = appointments,
                onBack = { finish() },
                onEdit = { appointment ->
                    showEditDateTimePicker(appointment)
                },
                onDelete = { appointment ->
                    bookingViewModel.deleteBooking(appointment)
                    Toast.makeText(this, "Booking cancelled", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun showEditDateTimePicker(appointment: Appointment) {
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

                        val newDate = "$day/${month + 1}/$year"
                        val newTime = String.format("%02d:%02d", hour, minute)

                        val updated = appointment.copy(
                            date = newDate,
                            time = newTime
                        )

                        bookingViewModel.updateBooking(updated)

                        Toast.makeText(
                            this,
                            "Booking updated to $newDate at $newTime",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingListScreen(
    appointments: List<Appointment>,
    onBack: () -> Unit,
    onEdit: (Appointment) -> Unit,
    onDelete: (Appointment) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Bookings") },
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

            if (appointments.isEmpty()) {

                Text(
                    text = "No bookings yet.",
                    style = MaterialTheme.typography.bodyLarge
                )

            } else {

                LazyColumn {
                    items(appointments) { booking ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Text(
                                    text = booking.serviceName,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(text = "Date: ${booking.date}")
                                Text(text = "Time: ${booking.time}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = { onEdit(booking) }) {
                                        Text("Edit")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    TextButton(onClick = { onDelete(booking) }) {
                                        Text("Cancel")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
