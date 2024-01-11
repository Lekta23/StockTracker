import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex
import network.data.StockSearchResult

@Composable
fun searchList( navigator: Navigator, symbols : List<StockSearchResult>) {
  print("searchList" + symbols)
  LazyColumn(
    contentPadding = PaddingValues(all = 8.dp),
    modifier = Modifier.padding(bottom = 56.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    items(symbols) { index ->
      searchRow(index, navigator)
    }
  }
}

@Composable
fun searchRow(stockIndex: StockSearchResult) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Text(text = stockIndex.symbol, modifier = Modifier.weight(1f))
    Text(text = "${stockIndex.description}", modifier = Modifier.weight(1f))
    IconButton(onClick = { /*TODO*/ }) {
      Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }
  }
}