package network.data
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val c: Double,  // Current price
    val d: Float?,  // Change (nullable)
    val dp: Float?, // Percent change (nullable)
    val h: Double,  // High price of the day
    val l: Double,  // Low price of the day
    val o: Double,  // Open price of the day
    val pc: Double, // Previous close price
    val t: Long     // Timestamp
)


@Serializable
data class StockSymbol(
    val currency: String?, // Currency of the price
    val description: String, // Description of the symbol
    val displaySymbol: String, // Display symbol name
    val figi: String?, // FIGI identifier
    val isin: String?, // ISIN, mainly for EU stocks and selected Asian markets
    val mic: String?, // Primary exchange's MIC
    val shareClassFIGI: String?, // Global Share Class FIGI
    val symbol: String, // Unique symbol for identification in /stock/candle endpoint
    val symbol2: String?, // Alternative ticker for certain exchanges
    val type: String // Security type
)


class FinnhubDataSource(private val apiKey: String) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getStockSymbols(exchange: String): List<StockSymbol> {
        val response: List<StockSymbol> = client.get("https://finnhub.io/api/v1/stock/symbol") {
            parameter("exchange", exchange)
            parameter("token", apiKey)
        }.body()
        return response
    }
    suspend fun getStockIndex(symbol: String): StockIndex {
        val response: QuoteResponse = client.get("https://finnhub.io/api/v1/quote") {
            parameter("symbol", symbol)
            parameter("token", apiKey)
        }.body()
        return mapResponseToFinnhubStockIndex(response, symbol)
    }

    private fun mapResponseToFinnhubStockIndex(response: QuoteResponse, symbol: String): StockIndex {
        return StockIndex(
            symbol = symbol,
            currentPrice = response.c,
            highPrice = response.h,
            lowPrice = response.l,
            openPrice = response.o,
            previousClosePrice = response.pc,
            percentChange = response.dp,
            change = response.d,
            timestamp = response.t
        )
    }
}

