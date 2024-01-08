
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp

@Composable
fun App() {
    PreComposeApp {
        StockAppTheme {
            rootNavHost()
        }
    }
}

expect fun getPlatformName(): String