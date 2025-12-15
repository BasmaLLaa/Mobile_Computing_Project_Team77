package com.example.salon

import kotlinx.coroutines.flow.Flow

class BookingRepository(
    private val appointmentDao: AppointmentDao
) {

    // READ all
    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    // CREATE
    suspend fun insert(appointment: Appointment) {
        appointmentDao.insert(appointment)
    }

    // UPDATE
    suspend fun update(appointment: Appointment) {
        appointmentDao.update(appointment)
    }

    // DELETE
    suspend fun delete(appointment: Appointment) {
        appointmentDao.delete(appointment)
    }
}
