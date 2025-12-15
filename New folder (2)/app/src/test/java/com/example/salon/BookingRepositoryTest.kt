package com.example.salon

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class BookingRepositoryTest {

    @Mock
    lateinit var mockDao: AppointmentDao

    private lateinit var repository: BookingRepository

    @Before
    fun setup() {
        // initialize @Mock fields
        MockitoAnnotations.openMocks(this)
        repository = BookingRepository(mockDao)
    }

    @Test
    fun insert_callsDaoInsert() = runTest {
        val appointment = Appointment(
            id = 1,
            serviceName = "Haircut",
            date = "10/12/2025",
            time = "10:00"
        )

        repository.insert(appointment)   // suspend function – OK inside runTest

        verify(mockDao).insert(appointment)
    }

    @Test
    fun update_callsDaoUpdate() = runTest {
        val appointment = Appointment(
            id = 2,
            serviceName = "Nails",
            date = "11/12/2025",
            time = "12:00"
        )

        repository.update(appointment)

        verify(mockDao).update(appointment)
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        val appointment = Appointment(
            id = 3,
            serviceName = "Hair Coloring",
            date = "12/12/2025",
            time = "14:00"
        )

        repository.delete(appointment)

        verify(mockDao).delete(appointment)
    }
}
