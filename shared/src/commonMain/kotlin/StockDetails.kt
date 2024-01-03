import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun StockDetails(stockIndex: StockIndex, navigator: Navigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Indices") },
            )
        },
        bottomBar = {
            BottomBar(navigator = navigator, currentRoute = "/index/${stockIndex.symbol}")
        }
    ) {
        Column(modifier = Modifier.padding(PaddingValues(all = 16.dp))) {
            Text(text = "Symbol: ${stockIndex.symbol}", style = MaterialTheme.typography.h6)
            Text(text = "Current Price: ${stockIndex.currentPrice}")
            Text(text = "High: ${stockIndex.highPrice}")
            Text(text = "Low: ${stockIndex.lowPrice}")
            Text(text = "Open: ${stockIndex.openPrice}")
            Text(text = "Previous Close: ${stockIndex.previousClosePrice}")
            stockIndex.percentChange?.let {
                Text(text = "Percent Change: $it%")
            }
            stockIndex.change?.let {
                Text(text = "Change: $it")
            }
        }
    }
}
