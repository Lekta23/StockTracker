
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver


actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    //context.startActivity(intent)
}

actual fun getPlatformName(): String = "Android"



@Composable fun MainView(context: Context) {
    val driverFactory = DriverFactory(context)
    App(driverFactory)
}
