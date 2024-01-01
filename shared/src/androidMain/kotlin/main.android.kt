import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.ui.platform.LocalContext

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()
