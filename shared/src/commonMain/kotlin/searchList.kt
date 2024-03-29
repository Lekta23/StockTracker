import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockRepository
import network.data.StockSearchResult

@Composable
fun searchList(navigator: Navigator, symbols: List<StockSearchResult>, stockRepository: StockRepository) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Search") },
        navigationIcon = {
          IconButton(onClick = { navigator.goBack() }) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Localized description"
            )
          }
        },
      )
    }
  ) {
    LazyColumn(
      contentPadding = PaddingValues(all = 8.dp),
      modifier = Modifier.padding(bottom = 56.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      if (symbols.isEmpty()) {
        item {
          Text("No results")
        }
      }
      items(symbols) { index ->
        searchRow(index, navigator, stockRepository)
      }
    }
  }
}

@Composable
fun searchRow(stockIndex: StockSearchResult, navigator: Navigator, stockRepository: StockRepository) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Text(text = stockIndex.symbol, modifier = Modifier.weight(1f))
    Text(text = "${stockIndex.description}", modifier = Modifier.weight(1f))
    IconButton(onClick = {
      // Use coroutineScope to call suspend function
      CoroutineScope(Dispatchers.Main).launch {
        stockRepository.addIndex(stockIndex.symbol)
        navigator.goBack()
        stockRepository.refreshWatchList()
      }
    }) {
      Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }
  }
}
