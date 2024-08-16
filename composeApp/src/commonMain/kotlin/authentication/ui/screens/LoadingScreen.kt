package authentication.ui.screens

import Login
import Onboarding
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
import core.DataPreferencesViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
            when {
                uiState.isOnboardingCompleted -> navController.navigate(Login)
                else -> navController.navigate(Onboarding)
            }
        }
    }
}