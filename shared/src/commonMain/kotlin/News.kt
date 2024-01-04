import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import moe.tlaster.precompose.navigation.Navigator
import network.data.NewsArticle

expect fun openUrl(url: String)

@Composable
fun News(navigator: Navigator, news: List<NewsArticle>) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("News") },
      )
    },
    bottomBar = {
      BottomBar(navigator = navigator, currentRoute = "/news")
    }
  ) {
    NewsList(news)
  }
}

@Composable
fun NewsList(news: List<NewsArticle>) {
  LazyColumn(
    contentPadding = PaddingValues(all = 8.dp),
    modifier = Modifier.padding(bottom = 56.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    items(news) { article ->
      NewsItem(article)
    }
  }
}

@Composable
fun NewsItem(article: NewsArticle) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    elevation = 2.dp
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Image(
        painter = rememberImagePainter(article.image),
        contentDescription = "Article Image",
        modifier = Modifier.fillMaxWidth()
      )
      Text(
        text = article.headline,
        style = MaterialTheme.typography.h6,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = article.summary,
        style = MaterialTheme.typography.body2,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
      )
      Button(onClick = { openUrl(article.url) }) {
        Text("Read More")
      }
    }
  }
}
