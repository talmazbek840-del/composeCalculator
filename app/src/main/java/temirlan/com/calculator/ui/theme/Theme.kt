package temirlan.com.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorScheme = CustomColorPalette(
    black3,
    white3,
    black2,
    white2,
    gray4,
    gray6,
    white5,
    gray2,
    black2
)
private val LightColorScheme = CustomColorPalette(
    white3,
    gray1,
    white2,
    gray5,
    white4,
    white3,
    gray1,
    white3,
    gray1
)
val LocalColors = staticCompositionLocalOf<CustomColorPalette> {
    error("Colors composition error")
}
@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (!darkTheme) LightColorScheme
    else DarkColorScheme

    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}
object MainTheme {
    val colors: CustomColorPalette
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
}