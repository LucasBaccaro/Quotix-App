package authentication.domain

import androidx.compose.ui.graphics.painter.Painter

data class OnboardingPageModel(
    val image: Painter,
    val title: String,
    val subtitle: String? = null,
    val steps: List<String> = emptyList()
)