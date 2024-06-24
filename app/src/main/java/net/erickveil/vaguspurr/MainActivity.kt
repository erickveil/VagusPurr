package net.erickveil.vaguspurr

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.erickveil.vaguspurr.ui.theme.VagusPurrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VagusPurrTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VibrationControl()
                }
            }
        }
    }
}

@Composable
fun VibrationControl() {
    var isVibrating by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    Button(
        onClick = {
            if (isVibrating) {
                vibrator.cancel()
            }
            else {
                if (vibrator.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val vibrationPattern =
                            VibrationEffect.createWaveform(longArrayOf(0, 100, 1),
                                intArrayOf(0, 255, 0), 0)
                        vibrator.vibrate(vibrationPattern)
                    }
                    vibrator.vibrate(longArrayOf(0, 100, 1), 0)
                }
            }
            isVibrating = !isVibrating
        },
        modifier = Modifier.padding(16.dp)
        ) {
        Text(text = if(isVibrating) "Stop Vibration" else "Start Vibration")
    }
}
