package my.id.femasaf.brodexter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import my.id.femasaf.brodexter.ui.dashboard.DashboardScreen
import my.id.femasaf.brodexter.ui.theme.BroDexterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BroDexterTheme {
                DashboardScreen()
            }
        }
    }
}
