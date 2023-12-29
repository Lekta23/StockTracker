import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun StockIndexList(stockIndices: List<StockIndex>) {
  LazyColumn {
    items(stockIndices) { index ->
      StockIndexRow(index)
    }
  }
}

@Composable
fun StockIndexRow(stockIndex: StockIndex) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
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
        navigationIcon = {
          IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) {
    StockIndexList(stockIndices)
  }
}