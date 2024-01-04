import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import network.data.StockRepository

private val stockRepository = StockRepository()

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
        StockDetails(stockIndex, navigator)
      }
    }
    scene(
      route = "/watchlist",
      navTransition = NavTransition(),
    ) {
        val stockIndices = stockRepository.watchlistState.collectAsState().value

        if (stockIndices.isNotEmpty()) {
          StockWatchList(navigator, stockIndices)
        }
    }
    scene(
      route = "/news",
      navTransition = NavTransition(),
    ) {
      News(navigator, stockRepository.newsState.collectAsState().value)
    }
  }
}