package authentication.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import authentication.domain.models.OnboardingPageModel
import coreui.PoppinsFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPager(
    pages: List<OnboardingPageModel>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        OnboardingPageContent(
            page = pages[page],
        )
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPageModel,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = page.image,
                contentDescription = null,
                modifier = Modifier.size(350.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = page.title,
                fontFamily = PoppinsFontFamily(),
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                textAlign = TextAlign.Center
            )
            if (page.steps.isEmpty()) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = page.subtitle ?: "",
                    fontFamily = PoppinsFontFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Stepper(page.steps)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}