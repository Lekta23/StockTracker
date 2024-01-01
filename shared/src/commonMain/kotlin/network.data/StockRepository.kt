package network.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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

class StockRepository : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val dataSource = FinnhubDataSource("cm5dc2pr01qjc6l49sh0cm5dc2pr01qjc6l49shg")
    private var _homeIndicesState =  MutableStateFlow(listOf<StockIndex>())
    val indicesState = _homeIndicesState.asStateFlow()

    private var _newsState = MutableStateFlow(listOf<NewsArticle>())
    val newsState = _newsState.asStateFlow()

    init {
        updateIndices()
        getNews()
    }

    private suspend fun getStockIndices(): List<StockIndex> {
        val symbols = listOf("AAPL", "GOOG", "TSLA", "AMZN", "MSFT")
        return symbols.mapNotNull { symbol ->
            try {
                dataSource.getStockIndex(symbol)
            } catch (e: Exception) {
                // Gérer l'exception, peut-être enregistrer l'erreur ou renvoyer null
                null
            }
        }
    }

    private fun getNews() {
        launch {
            while (isActive) {
                try {
                    val newsList = dataSource.getGeneralNews()
                    _newsState.value = newsList
                } catch (e: Exception) {
                    // Gérer l'exception, par exemple enregistrer l'erreur
                }
                delay(60000) // Mettre à jour les actualités toutes les 60 secondes, par exemple
            }
        }
    }

    private fun updateIndices() {
        launch {
            while (isActive) {
                _homeIndicesState.value = getStockIndices()
                delay(5000)
            }
        }
    }
}
