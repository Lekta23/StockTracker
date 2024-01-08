
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF003366),
    primaryVariant = Color(0xFF002244),
    secondary = Color(0xFF6699CC),
    error = Color(0xFFB00020),
    // ... autres couleurs
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF003366),
    primaryVariant = Color(0xFF002244),
    secondary = Color(0xFF6699CC),
    background = Color(0xFFF2F2F2),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color(0xFFB00020)
    // ... autres couleurs
)

@Composable
fun StockAppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        content = content
    )
}