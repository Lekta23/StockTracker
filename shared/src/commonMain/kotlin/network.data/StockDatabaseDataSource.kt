package network.data
import app.cash.sqldelight.db.SqlDriver
import com.stocktracker.db.StockTrackerDatabase
import kotlinx.coroutines.CoroutineScope

class StockDatabaseDataSource(private val sqlDriver: SqlDriver, private val coroutineScope: CoroutineScope) {

    private  var database=StockTrackerDatabase(sqlDriver)
    private  var dbQueries=database.stockTrackerDatabaseQueries
    fun storeIndex(stockIndex: StockIndex) {
        dbQueries.insertOrUpdateIndex(
            stockIndex.symbol,
            stockIndex.currentPrice,
            stockIndex.highPrice,
            stockIndex.lowPrice,
            stockIndex.openPrice,
            stockIndex.previousClosePrice,
            stockIndex.percentChange?.toDouble(),
            stockIndex.change?.toDouble(),
            stockIndex.timestamp
        )
    }

    fun getAllIndices(): List<StockIndex> {
        return dbQueries.selectAllIndices().executeAsList().map {
            StockIndex(
                it.symbol,
                it.currentPrice?.toDouble() ?: 0.0,
                it.highPrice?.toDouble() ?: 0.0,
                it.lowPrice?.toDouble() ?: 0.0,
                it.openPrice?.toDouble() ?: 0.0,
                it.previousClosePrice?.toDouble() ?: 0.0,
                it.percentChange?.toFloat(),
                it.change?.toFloat(),
                it.timestamp?.toLong() ?: 0L
            )
        }
    }

    fun getStockIndex(symbol: String): StockIndex? {
        return dbQueries.selectIndex(symbol).executeAsOneOrNull()?.let {
            StockIndex(
                it.symbol,
                it.currentPrice?.toDouble() ?: 0.0,
                it.highPrice?.toDouble() ?: 0.0,
                it.lowPrice?.toDouble() ?: 0.0,
                it.openPrice?.toDouble() ?: 0.0,
                it.previousClosePrice?.toDouble() ?: 0.0,
                it.percentChange?.toFloat(),
                it.change?.toFloat(),
                it.timestamp?.toLong() ?: 0L
            )
        }
    }

    // Implémentez d'autres méthodes si nécessaire
}