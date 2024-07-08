package net.erickveil.vaguspurr

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import net.erickveil.vaguspurr.ui.theme.VagusPurrTheme
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import net.erickveil.vaguspurr.ui.theme.VagusPurrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VagusPurrTheme{
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
    var isVibrating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    CustomButton(
        text = if (isVibrating) "Stop Vibration" else "Start Vibration",
        drawableId = R.drawable.vaguspur
    ) {
        if (isVibrating) {
            vibrator.cancel()
        } else {
            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val vibrationPattern = VibrationEffect.createWaveform(
                        longArrayOf(0, 100, 1), intArrayOf(0, 255, 0), 0
                    )
                    vibrator.vibrate(vibrationPattern)
                } else {
                    vibrator.vibrate(longArrayOf(0, 100, 1), 0)
                }
            }
        }
        isVibrating = !isVibrating
    }
}

@Composable
fun CustomButton(text: String, drawableId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F5DC)
        ),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null,
                modifier = Modifier
                    .size(144.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
        }
    }
}

