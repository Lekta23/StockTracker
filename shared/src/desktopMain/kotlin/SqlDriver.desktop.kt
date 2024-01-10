import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.stocktracker.db.StockTrackerDatabase


actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:stocktracker.db")
        StockTrackerDatabase.Schema.create(driver)
        return driver
    }
}
