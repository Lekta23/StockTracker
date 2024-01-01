import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() = App()

@Preview
@Composable
fun AppPreview() {
    App()
}

actual fun openUrl(url: String) {
    java.awt.Desktop.getDesktop()?.browse(java.net.URI(url))
}