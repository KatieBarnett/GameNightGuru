package dev.katiebarnett.gamenightguru.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CoralRed,
    secondary = SkyBlue,
    tertiary = GoldenYellow
)

private val LightColorScheme = lightColorScheme(
    primary = CoralRedDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDADE),
    onPrimaryContainer = Color(0xFF410015),
    secondary = SkyBlueDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFBFE9FF),
    onSecondaryContainer = Color(0xFF001F2A),
    tertiary = GoldenYellowDark,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFFFE082),
    onTertiaryContainer = Color(0xFF241A00),
    background = Color(0xFFF0F9FF), // Very soft sky blue background
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF201A19),
    surfaceVariant = Color(0xFFF4DDDE),
    onSurfaceVariant = Color(0xFF534346),
)


@Composable
fun GameNightGuruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor to false by default to ensure our custom "Playful & Bold" 
    // theme is applied instead of the system's dynamic colors.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}