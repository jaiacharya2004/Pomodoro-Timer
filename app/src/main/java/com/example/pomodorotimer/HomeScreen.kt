package com.example.pomodorotimer

import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun HomeScreen(timerViewModel: TimerViewModel = remember { TimerViewModel() }, modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pomodoro Timer",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = { /* Handle button click */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.self_improvement_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = "Icon Button"
                        )
                    }
                    Spacer(modifier = Modifier.width(46.dp))
                    IconButton(onClick = { /* Handle button click */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.timelapse_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = "Icon Button"
                        )
                    }
                    Spacer(modifier = Modifier.width(56.dp))
                    IconButton(onClick = { /* Handle button click */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.fullscreen_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = "Icon Button"
                        )
                    }
                    Spacer(modifier = Modifier.width(60.dp))
                    IconButton(onClick = { /* Handle button click */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.headphones_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = "Icon Button"
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
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
                    TimerDisplay(timerViewModel.currentSeconds.value)

                    Spacer(modifier = Modifier.height(24.dp))

                    TimerControls(timerViewModel)
                }
            }
        }
    )
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

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
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
                contentDescription = "Icon Button"
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = { timerViewModel.resetTimer() }) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset")
        }
    }
}






