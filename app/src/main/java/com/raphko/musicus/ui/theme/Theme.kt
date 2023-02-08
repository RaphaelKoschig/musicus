package com.raphko.musicus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = DarkTeal,
    primaryVariant = VividTeal,
    secondary = DarkGrey,
    secondaryVariant = DarkMinGrey
)

private val LightColorPalette = lightColors(
    primary = VividTeal,
    primaryVariant = DarkTeal,
    secondary = DarkGrey,
    secondaryVariant = DarkMinGrey,
    surface = BoldRed

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MusicusTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    if(darkTheme){
        systemUiController.setSystemBarsColor(
            color = VividTeal
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = DarkTeal
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}