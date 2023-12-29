package network.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


data class StockIndex(
    val symbol: String,
    val currentPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val previousClosePrice: Double,
    val percentChange: Float?, // Change to nullable
    val change: Float?,        // Change to nullable
    val timestamp: Long
)


class StockRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val dataSource = FinnhubDataSource("")
    private var _indicesState=  MutableStateFlow(listOf<StockIndex>())
    var indicesState = _indicesState
    init {
        updateIndices()
    }
    suspend fun getStockIndices(): List<StockIndex> {
        val symbols = dataSource.getStockSymbols("US").take(5)
        return symbols.mapNotNull { symbol ->
            dataSource.getStockIndex(symbol.symbol)
        }
    }
    private fun updateIndices() {
        coroutineScope.launch {
            while (true) {
                delay(60000)
                _indicesState.value = getStockIndices()
            }
        }
    }
}
