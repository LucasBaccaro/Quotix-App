import androidx.compose.ui.window.ComposeUIViewController
import authentication.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}