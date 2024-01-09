import app.cash.sqldelight.db.SqlDriver
import com.stocktracker.db.StockTrackerDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): StockTrackerDatabase {
    val driver = driverFactory.createDriver()
    val database = StockTrackerDatabase(driver)

    // Do more work with the database (see below).

    return database
}