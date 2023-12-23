package network.data

data class StockIndex(
  val name: String,
  val value: Double,
  val percentChange: Double,
  val netChange: Double
)

class StockRepository {
  fun getStockIndices(): List<StockIndex> {
    return listOf(
      StockIndex("Dow Jones", 35000.0, 0.1, 35.0),
      StockIndex("S&P 500", 4500.0, -0.2, 9.0),
      StockIndex("Nasdaq", 15000.0, 0.3, 45.0),
    )
  }
}
