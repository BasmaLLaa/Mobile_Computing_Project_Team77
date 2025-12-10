package com.example.salon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onBookClick = {
                    val intent = Intent(this, ServiceListActivity::class.java)
                    startActivity(intent)
                },
                onViewBookingsClick = {
                    val intent = Intent(this, BookingListActivity::class.java)
                    startActivity(intent)
                },
                onContactClick = {
                    // TODO: add contact screen later
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    onBookClick: () -> Unit,
    onViewBookingsClick: () -> Unit,
    onContactClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ⭐ NEW: Added illustration image
                Image(
                    painter = painterResource(id = R.drawable.beauty_salon_amico),
                    contentDescription = "Salon Illustration",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(bottom = 24.dp),
                    contentScale = ContentScale.Fit
                )

                // Welcome card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp,
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome to",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = "My Salon",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = onBookClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Book Appointment", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onViewBookingsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "My Bookings", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onContactClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Contact Us", fontSize = 18.sp)
                }
            }
        }
    }
}
