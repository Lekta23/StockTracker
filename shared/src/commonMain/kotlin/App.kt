
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import moe.tlaster.precompose.PreComposeApp
import network.data.StockDatabaseDataSource
import network.data.StockRepository

@Composable
fun App(driverFactory: DriverFactory) {
    val sqlDriver = driverFactory.createDriver()
    val dbDataSource = StockDatabaseDataSource(sqlDriver, CoroutineScope(Dispatchers.IO))
    val stockRepository = StockRepository(dbDataSource)
    PreComposeApp {
        StockAppTheme {
            rootNavHost(stockRepository)
        }
    }
}

expect fun getPlatformName(): String