package authentication.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coreui.PoppinsFontFamily

@Composable
fun NavigationButtons(
    isLastPage: Boolean,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonModifier = Modifier
        .background(Color.Black, shape = RoundedCornerShape(50))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isLastPage) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        if (isLastPage) {
            TextButton(
                onClick = onStart,
                modifier = buttonModifier.width(250.dp)
            ) {
                Text(
                    text = "START",
                    fontFamily = PoppinsFontFamily(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        } else {
            TextButton(
                onClick = onSkip,
            ) {
                Text(
                    text = "SKIP",
                    fontFamily = PoppinsFontFamily(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            TextButton(
                onClick = onNext,
                modifier = buttonModifier.width(98.dp)
            ) {
                Text(
                    text = "NEXT",
                    fontFamily = PoppinsFontFamily(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}