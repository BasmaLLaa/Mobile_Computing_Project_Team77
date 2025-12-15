package com.example.salon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
                    startActivity(Intent(this, ServiceListActivity::class.java))
                },
                onViewBookingsClick = {
                    startActivity(Intent(this, BookingListActivity::class.java))
                },
                onContactClick = {
                    openFlutterContactApp()
                }
            )
        }
    }

    private fun openFlutterContactApp() {
        val uri = Uri.parse("flutterapp://contact?msg=fromSalon")
        val intent = Intent(Intent.ACTION_VIEW, uri)

        // Find all activities that can handle this deep link
        val matches = packageManager.queryIntentActivities(intent, 0)

        // Pick one that is NOT this salon app
        val target = matches.firstOrNull { it.activityInfo.packageName != packageName }

        if (target != null) {
            val explicit = Intent(intent).apply {
                setClassName(target.activityInfo.packageName, target.activityInfo.name)

                // ✅ open Flutter app as a separate app/task
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(explicit)
        } else {
            Toast.makeText(
                this,
                "Flutter Contact app not installed (or deep link not registered).",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
fun HomeScreen(
    onBookClick: () -> Unit,
    onViewBookingsClick: () -> Unit,
    onContactClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
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
                Image(
                    painter = painterResource(id = R.drawable.beauty_salon_amico),
                    contentDescription = "Salon Illustration",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(bottom = 24.dp),
                    contentScale = ContentScale.Fit
                )

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
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "My Salon",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
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
