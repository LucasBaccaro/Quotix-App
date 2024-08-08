package authentication.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import authentication.domain.models.OnboardingPageModel
import core.DataPreferencesViewModel
import authentication.ui.screens.components.NavigationButtons
import authentication.ui.screens.components.OnboardingPager
import authentication.ui.screens.components.PageIndicator
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import quotix.composeapp.generated.resources.Res
import quotix.composeapp.generated.resources.ob1
import quotix.composeapp.generated.resources.ob2
import quotix.composeapp.generated.resources.ob3
import quotix.composeapp.generated.resources.welcome


@OptIn(KoinExperimentalAPI::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    dataPreferencesViewModel: DataPreferencesViewModel = koinViewModel()
) {
    val pages = listOf(
        OnboardingPageModel(
            image = painterResource(Res.drawable.ob1),
            title = stringResource(Res.string.welcome),
            subtitle = "Administra tus entidades de manera sencilla",
        ),
        OnboardingPageModel(
            image = painterResource(Res.drawable.ob2),
            title = "Funcionalidades claves",
            steps = listOf(
                "Creación de entidades",
                "Gestión de socios",
                "Cobros mensuales"
            )
        ),
        OnboardingPageModel(
            image = painterResource(Res.drawable.ob3),
            title = "Beneficios de usar Quotix",
            steps = listOf(
                "Organización de tu entidad",
                "Máximo control y seguridad",
                "Facilidad de uso"
            )
        )
    )

    OnboardingContent(pages = pages, onStart = {
        dataPreferencesViewModel.completeOnboarding()
        navController.navigate("create") {
            popUpTo("onboarding") { inclusive = true }
        }
    })
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingContent(
    pages: List<OnboardingPageModel>,
    onStart: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { pages.size })
        val coroutineScope = rememberCoroutineScope()

        OnboardingPager(
            pages = pages,
            pagerState = pagerState,
            modifier = Modifier.weight(1f),
        )

        PageIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage,
        )

        NavigationButtons(
            isLastPage = pagerState.currentPage == pages.size - 1,
            onSkip = { coroutineScope.launch { pagerState.scrollToPage(pages.size - 1) } },
            onNext = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
            onStart = {
                onStart()
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
