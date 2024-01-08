
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable

actual fun openUrl(url: String) {
    val context = MyApplication.appContext ?: return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

class MyApplication: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}