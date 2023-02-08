package com.raphko.musicus.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.raphko.musicus.R
import com.raphko.musicus.utils.MusicusMediaPlayer

// A box to manage action on the mediaplayer singleton, it's displayed everywhere
@Composable
fun PlayerBoxUi() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .zIndex(1f)
            .absoluteOffset(screenWidth - 100.dp, screenHeight - 100.dp)
            .size(80.dp, 80.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    MusicusMediaPlayer.shared.stopTrack()
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp),
                    painter = painterResource(R.drawable.stop_icon_128),
                    contentDescription = "Stop music"
                )
            }
        }
    }
}