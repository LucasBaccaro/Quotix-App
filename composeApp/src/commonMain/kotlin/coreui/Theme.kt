package coreui
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import quotix.composeapp.generated.resources.Poppins_Bold
import quotix.composeapp.generated.resources.Poppins_Regular
import quotix.composeapp.generated.resources.Res

@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.Poppins_Bold , weight = FontWeight.Bold),
    Font(Res.font.Poppins_Regular, weight = FontWeight.Normal),
)
