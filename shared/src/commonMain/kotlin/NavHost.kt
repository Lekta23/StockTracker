import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
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
    initialRoute = "/welcome",
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

      if (stockIndices.isNotEmpty()) {
        StockIndex(navigator, stockIndices)
      }
    }
    scene(
      route = "/watchlist",
      navTransition = NavTransition(),
    ) {
      WatchList(navigator)
    }
    scene(
      route = "/news",
      navTransition = NavTransition(),
    ) {
      News(navigator)
    }
  }
}