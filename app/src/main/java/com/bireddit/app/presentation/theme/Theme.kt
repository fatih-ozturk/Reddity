/*
 * Copyright 2022 Fatih OZTURK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bireddit.app.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Stable
class BiRedditColors(
    gradient1: List<Color>,
    colorPrimary: Color,
    colorPrimaryVariant: Color,
    colorBackground: Color,
    colorSurface: Color,
    colorError: Color,
    colorButtonVariant: Color,
    textColor: Color,
    textColorVariant: Color
) {
    var gradient1 by mutableStateOf(gradient1)
        private set
    var colorPrimary by mutableStateOf(colorPrimary)
        private set
    var colorPrimaryVariant by mutableStateOf(colorPrimaryVariant)
        private set
    var colorBackground by mutableStateOf(colorBackground)
        private set
    var colorSurface by mutableStateOf(colorSurface)
        private set
    var colorError by mutableStateOf(colorError)
        private set
    var colorButtonVariant by mutableStateOf(colorButtonVariant)
        private set
    var textColor by mutableStateOf(textColor)
        private set
    var textColorVariant by mutableStateOf(textColorVariant)
        private set

    fun update(other: BiRedditColors) {
        gradient1 = other.gradient1
        colorPrimary = other.colorPrimary
        colorPrimaryVariant = other.colorPrimaryVariant
        colorBackground = other.colorBackground
        colorSurface = other.colorSurface
        colorError = other.colorError
        colorButtonVariant = other.colorButtonVariant
        textColor = other.textColor
        textColorVariant = other.textColorVariant
    }

    fun copy(): BiRedditColors = BiRedditColors(
        gradient1 = gradient1,
        colorPrimary = colorPrimary,
        colorPrimaryVariant = colorPrimaryVariant,
        colorBackground = colorBackground,
        colorSurface = colorSurface,
        colorError = colorError,
        colorButtonVariant = colorButtonVariant,
        textColor = textColor,
        textColorVariant = textColorVariant
    )
}

private val lightColorPalette = BiRedditColors(
    colorPrimary = redRibbon,
    colorPrimaryVariant = scienceBlue,
    colorBackground = white,
    colorSurface = athensGray,
    colorError = vermilion,
    colorButtonVariant = ceruleanBlue,
    textColor = codGray,
    textColorVariant = boulder,
    gradient1 = listOf(redRibbon, goldenBell)
)

@Composable
fun BiRedditTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = lightColorPalette

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.colorBackground.copy(alpha = 0.4f)
        )
    }
    ProvideBiRedditColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun ProvideBiRedditColors(
    colors: BiRedditColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors.copy() }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalBiRedditColors provides colorPalette, content = content)
}

private val LocalBiRedditColors = staticCompositionLocalOf<BiRedditColors> {
    error("No BiRedditColorPalette provided")
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)
