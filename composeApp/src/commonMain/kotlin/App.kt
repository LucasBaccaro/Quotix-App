import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import authentication.ui.AuthViewModel
import authentication.ui.screens.CreateScreen
import authentication.ui.screens.LoadingScreen
import authentication.ui.screens.LoginScreen
import authentication.ui.screens.OnboardingScreen
import home.ui.HomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun App() {
    StartNavigation()
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun StartNavigation() {
    val navController = rememberNavController()
    val authViewModel = koinViewModel<AuthViewModel>()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Loading
    ) {
        composable<Loading> {
            LoadingScreen(navController = navController)
        }
        composable<Onboarding> {
            OnboardingScreen(goToCreate = { navController.navigate(Authentication) })
        }
        composable<Login> {
            LoginScreen(
                goToCreateAccount = { navController.navigate(Authentication) },
                goToHome = { id -> navController.navigate(Home(params = id)) },
                authViewModel = authViewModel
            )
        }
        composable<Authentication> {
            CreateScreen(
                authViewModel = authViewModel,
                onBack = { navController.popBackStack() },
                goToHome = { id -> navController.navigate(Home(params = id)) }
            )
        }
        composable<Home> {
            val args = it.toRoute<Home>()
            HomeScreen(args.params, onBack = { navController.popBackStack() })
        }
    }
}