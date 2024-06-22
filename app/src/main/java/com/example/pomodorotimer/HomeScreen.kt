package com.example.pomodorotimer

import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(timerViewModel: TimerViewModel = remember { TimerViewModel() }) {
    var timerExpanded by remember { mutableStateOf(false) }
    var selectedTimer by remember { mutableStateOf(25) } // Default to 25 minutes

    Scaffold(
        // TopBar and BottomBar setup
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Clickable display for the selected timer (e.g., 25 min)
                    Text(
                        text = "${selectedTimer} min",
                        fontSize = 48.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                timerExpanded = !timerExpanded
                            }
                            .padding(16.dp)
                            .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )

                    // Expandable list of timer options
                    if (timerExpanded) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp)
                                .background(color = Color.LightGray)
                        ) {
                            // Scroll down from selectedTimer to 1
                            items((selectedTimer..1).reversed().toList()) { minutes ->
                                TimerSelectorItem(
                                    minutes = minutes,
                                    onClick = {
                                        selectedTimer = minutes // Update selectedTimer
                                        timerViewModel.setTimerDuration(minutes * 60)
                                        timerExpanded = false
                                    }
                                )
                            }

                            // Scroll up from selectedTimer + 1 to 60 (adjust upper bound as needed)
                            items((selectedTimer + 1..480).toList()) { minutes ->
                                TimerSelectorItem(
                                    minutes = minutes,
                                    onClick = {
                                        selectedTimer = minutes // Update selectedTimer
                                        timerViewModel.setTimerDuration(minutes * 60)
                                        timerExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    TimerDisplay(timerViewModel.currentSeconds.value)

                    Spacer(modifier = Modifier.height(24.dp))

                    TimerControls(timerViewModel)
                }
            }
        }
    )
}


@Composable
fun TimerSelectorItem(minutes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
    ) {
        Text(
            text = "${minutes} min",
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TimerDisplay(currentSeconds: Int) {
    Text(
        text = formatTime(currentSeconds),
        fontSize = 48.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TimerControls(timerViewModel: TimerViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { timerViewModel.startTimer() }) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Start")
        }
        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = { timerViewModel.pauseTimer() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.pause_24dp_fill0_wght400_grad0_opsz24),
                contentDescription = "Pause"
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = { timerViewModel.resetTimer() }) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset")
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}



