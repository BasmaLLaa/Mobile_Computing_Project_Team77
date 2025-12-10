package com.example.salon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookingRepository
    val appointments: Flow<List<Appointment>>

    init {
        val db = SalonDatabase.getInstance(application)
        val dao = db.appointmentDao()
        repository = BookingRepository(dao)
        appointments = repository.allAppointments
    }

    // CREATE
    fun book(serviceName: String, date: String, time: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(
                Appointment(
                    serviceName = serviceName,
                    date = date,
                    time = time
                )
            )
        }
    }

    // UPDATE
    fun updateBooking(updated: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(updated)
        }
    }

    // DELETE
    fun deleteBooking(appointment: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(appointment)
        }
    }
}
