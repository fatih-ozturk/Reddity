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
package com.bireddit.app.composeui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Immutable
data class ExtendedColors(
    val onBackgroundVariant: Color,
    val success: Color,
    val indicator: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        onBackgroundVariant = Color.Unspecified,
        success = Color.Unspecified,
        indicator = Color.Unspecified
    )
}

val extendedColors = ExtendedColors(
    onBackgroundVariant = catskillWhite,
    success = emerald,
    indicator = caribbeanGreen
)

val lightColors = lightColors(
    primary = vermilion,
    primaryVariant = pumpkin,
    secondary = toryBlue,
    secondaryVariant = scienceBlue,
    surface = white,
    onPrimary = white,
    onSecondary = white,
    onError = white,
    background = athensGray,
    error = redRibbon,
    onBackground = rollingStone,
    onSurface = codGray
)

@Composable
fun BiRedditTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = lightColors.surface
        )
    }
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colors = lightColors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
