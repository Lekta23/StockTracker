import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
@Composable
fun BottomBar(navigator: Navigator, currentRoute: String) {
  BottomNavigation {
    BottomNavigationItem(
      icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
      label = { Text("Home") },
      selected = currentRoute == "/index",
      onClick = {
        if (currentRoute != "/index"){
          navigator.navigate(route = "/index")
        }
      }
    )
    BottomNavigationItem(
      icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite") },
      label = { Text("Favorite") },
      selected = currentRoute == "/watchlist",
      onClick = {
        if (currentRoute != "/watchlist"){
          navigator.navigate(route = "/watchlist")
        }
      }
    )
    BottomNavigationItem(
      icon = { Icon(Icons.Default.List, contentDescription = "News") },
      label = { Text("News") },
      selected = currentRoute == "/news",
      onClick = {
        if (currentRoute != "/news"){
          navigator.navigate(route = "/news")
        }
      }
    )
  }
}