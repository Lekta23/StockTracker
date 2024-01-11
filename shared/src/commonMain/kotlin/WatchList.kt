
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import network.data.StockIndex
@Composable
fun StockWatchList(
  navigator: Navigator,
  stockIndices: List<StockIndex>,
  coroutineScope: CoroutineScope // Explicitly pass CoroutineScope
) {
  var isSearching by remember { mutableStateOf(false) }
  var searchText by remember { mutableStateOf("") }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Watch List") },
        actions = {
          IconButton(onClick = {
            isSearching = true
          }) {
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
    if (isSearching) {
      SearchDialog(
        navigator = navigator,
        searchText = searchText,
        onSearchTextChange = { newText -> searchText = newText },
        onDismissRequest = { isSearching = false },
        coroutineScope = coroutineScope // Pass CoroutineScope to SearchDialog
      )
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchDialog(
  navigator: Navigator,
  searchText: String,
  onSearchTextChange: (String) -> Unit,
  onDismissRequest: () -> Unit,
  coroutineScope: CoroutineScope // Explicitly pass CoroutineScope
) {
  var isFocused by remember { mutableStateOf(true) }
  val keyboardController = LocalSoftwareKeyboardController.current

  AlertDialog(
    onDismissRequest = {
      onDismissRequest()
    },
    title = {
      TextField(
        value = searchText,
        onValueChange = {
          onSearchTextChange(it)
        },
        modifier = Modifier
          .fillMaxWidth()
          .onGloballyPositioned {
            if (isFocused) {
              keyboardController?.show()
              isFocused = false
            }
          },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            keyboardController?.hide()
            onDismissRequest()
          }
        ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = Color.Transparent,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
    },
    confirmButton = {
      Button(
        onClick = {
          coroutineScope.launch { // Use the provided CoroutineScope
            stockRepository.searchSymbol(searchText)
            navigator.navigate("/search")
          }
        }
      ) {
        Text("Search")
      }
    },
    dismissButton = {
      Button(
        onClick = {
          onDismissRequest()
        }
      ) {
        Text("Cancel")
      }
    }
  )
}
