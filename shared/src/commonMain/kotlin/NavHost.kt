
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import network.data.StockDatabaseDataSource
import network.data.StockRepository


val driverFactory = DriverFactory()
val sqlDriver = driverFactory.createDriver()
val dbDataSource = StockDatabaseDataSource(sqlDriver, CoroutineScope(Dispatchers.IO))
val stockRepository = StockRepository(dbDataSource)

@Composable
internal fun rootNavHost() {
  val navigator = rememberNavigator()
  NavHost(
    navigator = navigator,
    navTransition = NavTransition(),
    initialRoute = "/index",
  ) {
    scene(
      route = "/welcome",
      navTransition = NavTransition(),
    ) {
      welcome(navigator)
    }
    scene(
      route = "/index",
      navTransition = NavTransition(),
    ) {

      val stockIndices = stockRepository.indicesState.collectAsState().value
      val news = stockRepository.homeNewsState.collectAsState().value

      if (stockIndices.isNotEmpty()) {
        StockIndex(navigator, stockIndices, news)
      }
    }
scene(
      route = "/index/{symbol}",
      navTransition = NavTransition(),
    ) {
      val symbol = it.path<String>("symbol")
      val stockIndex = stockRepository.watchlistState.collectAsState().value.firstOrNull { it.symbol == symbol }
      if (stockIndex != null) {
        StockDetails(stockIndex, navigator, stockRepository)
      }
    }
    scene(
      route = "/watchlist",
      navTransition = NavTransition(),
    ) {
        val stockIndices = stockRepository.watchlistState.collectAsState().value

        if (stockIndices.isNotEmpty()) {
          StockWatchList(navigator, stockIndices, stockRepository)
        }
    }
    scene(
      route = "/news",
      navTransition = NavTransition(),
    ) {
      News(navigator, stockRepository.newsState.collectAsState().value)
    }

    scene(
      route = "/search",
      navTransition = NavTransition(),
    ) {
      searchList(navigator, stockRepository.searchState.collectAsState().value)
    }
  }
}