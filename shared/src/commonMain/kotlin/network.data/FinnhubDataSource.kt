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

@Serializable
data class StockSearchResponse(
    val count: Int,
    val result: List<StockSearchResult>
)

@Serializable
data class StockSearchResult(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String
)

@Serializable
data class NewsArticle(
    val category: String,
    val datetime: Long,
    val headline: String,
    val id: Int,
    val image: String,
    val related: String,
    val source: String,
    val summary: String,
    val url: String
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
    // Méthode pour récupérer les nouvelles
    suspend fun getGeneralNews(): List<NewsArticle> {
        return try {
            val response: List<NewsArticle> = client.get("https://finnhub.io/api/v1/news") {
                parameter("category", "general")
                parameter("token", apiKey)
            }.body()
            response
        } catch (e: Exception) {
            // Gérer l'exception, retourner une liste vide ou relancer
            emptyList()
        }
    }
    private fun mapResponseToFinnhubStockIndex(response: QuoteResponse, symbol: String): StockIndex {
        return StockIndex(
            symbol = symbol,
            currentPrice = response.c,
            highPrice = response.h ?: 0.0,
            lowPrice = response.l ?: 0.0,
            openPrice = response.o ?: 0.0,
            previousClosePrice = response.pc ?: 0.0,
            percentChange = response.dp ?: 0f,
            change = response.d ?: 0f,
            timestamp = response.t
        )
    }

    suspend fun searchSymbol(query: String): List<StockSearchResult> {
        return try {
            val response: StockSearchResponse = client.get("https://finnhub.io/api/v1/search") {
                parameter("q", query)
                parameter("token", apiKey)
            }.body()
            response.result;
        } catch (e: Exception) {
            // Gérer l'exception, retourner une liste vide ou relancer
            emptyList()
        }
    }

}

