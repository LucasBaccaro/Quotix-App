import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import authentication.ui.screens.AuthScreen
import authentication.ui.DataPreferencesViewModel
import authentication.ui.screens.OnboardingScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "loading"
    ) {
        composable("loading") {
            LoadingScreen(navController = navController)
        }
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        composable("authentication") {
            AuthScreen()
        }
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoadingScreen(
    dataPreferencesViewModel: DataPreferencesViewModel = koinViewModel(),
    navController: NavController
) {
    val uiState by dataPreferencesViewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.Black)
    }
    LaunchedEffect(uiState.isLoading) {
        if (!uiState.isLoading) {
            if (uiState.isOnboardingCompleted) {
                navController.navigate("authentication")
            } else {
                navController.navigate("onboarding")
            }
        }
    }

}








