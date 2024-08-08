import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import authentication.ui.AuthViewModel
import authentication.ui.screens.CreateScreen
import authentication.ui.screens.LoadingScreen
import authentication.ui.screens.LoginScreen
import authentication.ui.screens.OnboardingScreen
import home.ui.HomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    val ID = "id"
    val navController = rememberNavController()
    val authViewModel = koinViewModel<AuthViewModel>()
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
        composable("create") {
            CreateScreen(
                authViewModel = authViewModel,
                onBack = { navController.popBackStack() })
        }
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                goToHome = { id ->
                    navController.navigate("home/$id")
                },
                goToCreateAccount = { navController.navigate("create") }
            )
        }
        composable(
            "home/{$ID}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(ID) ?: ""
            HomeScreen(id = id)
        }
    }
}