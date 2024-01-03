
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun StockIndexList(stockIndices: List<StockIndex>, navigator: Navigator) {
  LazyColumn {
    items(stockIndices) { index ->
      StockIndexRow(index, navigator)
    }
  }
}

@Composable
fun StockIndexRow(stockIndex: StockIndex, navigator: Navigator) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clickable {
        navigator.navigate(route = "/index/${stockIndex.symbol}")
      }
  ) {
    Text(text = stockIndex.symbol, modifier = Modifier.weight(1f))
    Text(text = "${stockIndex.currentPrice}", modifier = Modifier.weight(1f))
    Text(text = stockIndex.percentChange?.toString()+"%", color = if (stockIndex.percentChange!! >= 0) Color.Green else Color.Red)
  }
}



@Composable
fun StockIndex(navigator: Navigator, stockIndices: List<StockIndex>) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Stock Indices") },
      )
    },
    bottomBar = {
      BottomBar(navigator = navigator, currentRoute = "/index")
    }
  ) {
    StockIndexList(stockIndices, navigator)
  }

}