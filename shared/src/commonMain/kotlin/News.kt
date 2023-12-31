import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun News(navigator: Navigator) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("") },
      )
    },
    bottomBar = {
      BottomBar(navigator = navigator, currentRoute = "/news")
    }
  ) {
    Text("News")
  }
}