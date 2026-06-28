package my.id.femasaf.brodexter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import my.id.femasaf.brodexter.ui.dashboard.DashboardScreen
import my.id.femasaf.brodexter.ui.dashboard.DashboardViewModel
import my.id.femasaf.brodexter.ui.history.HistoryScreen
import my.id.femasaf.brodexter.ui.theme.BroDexterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BroDexterTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    // Inisialisasi ViewModel di level ini agar bisa di-share antar screen
    val sharedViewModel: DashboardViewModel = viewModel()
    
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                viewModel = sharedViewModel,
                onSeeAllClick = { navController.navigate("history") }
            )
        }
        composable("history") {
            HistoryScreen(
                viewModel = sharedViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
