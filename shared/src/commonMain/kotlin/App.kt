import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp

@Composable
fun App() {
    PreComposeApp {
        MaterialTheme {
            rootNavHost()
        }
    }
}

expect fun getPlatformName(): String