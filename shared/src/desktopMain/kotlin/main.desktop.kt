import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() = App(DriverFactory())

@Preview
@Composable
fun AppPreview() {
    App(DriverFactory())
}
actual fun openUrl(url: String) {
    java.awt.Desktop.getDesktop().browse(java.net.URI(url))
}