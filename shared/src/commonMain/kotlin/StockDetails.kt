
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex

@Composable
fun StockDetails(stockIndex: StockIndex, navigator: Navigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Indices") },
                navigationIcon = {
                    IconButton(onClick = { navigator.goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add stock to the watch list"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete stock from the watch list"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomBar(navigator = navigator, currentRoute = "/index/${stockIndex.symbol}")
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stockIndex.symbol,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "\$${stockIndex.currentPrice}",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            StockDetailRow(label = "High", value = "\$${stockIndex.highPrice}")
            StockDetailRow(label = "Low", value = "\$${stockIndex.lowPrice}")
            StockDetailRow(label = "Open", value = "\$${stockIndex.openPrice}")
            StockDetailRow(label = "Previous Close", value = "\$${stockIndex.previousClosePrice}")
            StockValueChangeRow(
                label = "Percent Change",
                value = stockIndex.percentChange?.let { "$it%" } ?: "N/A",
                isPositive = stockIndex.percentChange?.let { it > 0 } ?: false
            )
            StockValueChangeRow(
                label = "Change",
                value = stockIndex.change?.toString() ?: "N/A",
                isPositive = stockIndex.change?.let { it > 0 } ?: false
            )
            timeLastUpdated(stockIndex.timestamp)
        }
    }
}


@Composable
fun StockDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun StockValueChangeRow(label: String, value: String, isPositive: Boolean) {
    val color = if (isPositive) Color.Green else Color.Red
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Normal,
            color = color
        )
    }
}

@Composable
fun timeLastUpdated(time: Long) {
    Text(
        text = "Last updated: ${time}",
        style = MaterialTheme.typography.caption,
        fontWeight = FontWeight.Normal
    )
}
