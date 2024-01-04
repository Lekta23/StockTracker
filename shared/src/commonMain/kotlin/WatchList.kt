
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun StockWatchList(navigator: Navigator, stockIndices: List<StockIndex>) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Watch List") },
        actions = {
          IconButton(onClick = { /* do something */ }) {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search stocks"
            )
          }
        },
      )
    },
    bottomBar = {
      BottomBar(navigator = navigator, currentRoute = "/watchlist")
    }
  ) {
    StockIndexList(stockIndices, navigator)
  }

}