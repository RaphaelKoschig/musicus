package com.raphko.musicus.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raphko.musicus.model.Track
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.ui.viewmodel.ArtistViewModel
import com.raphko.musicus.utils.MusicusMediaPlayer

@Composable
fun TracklistUi(tracks: List<Track>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(tracks) { index, track ->
            val paddingTop = if (index == 0) 30.dp else 10.dp
            Row(
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = paddingTop,
                    bottom = 10.dp
                )
            ) {
                TrackCard(
                    track,
                    index
                )
            }
        }
    }
}

@Composable
private fun TrackCard(track: Track, index: Int) {
    val trackBeingPlayed by MusicusMediaPlayer.shared.trackBeingPlayed.collectAsState()
    val context = LocalContext.current
    val surfaceColor by animateColorAsState(
        if (trackBeingPlayed == track.id) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
    )
    val titleColor by animateColorAsState(
        if (trackBeingPlayed == track.id) Color.White else Color.LightGray,
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                MusicusMediaPlayer.shared.playTrack(context, track)
            },
        shape = RoundedCornerShape(4.dp),
        backgroundColor = surfaceColor
    ) {
        Row() {
            Text(
                modifier = Modifier.padding(10.dp, 10.dp),
                text = "${index + 1}. ${track.title}",
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }
    }
}

@Preview
@Composable
fun PreviewTrackListUi() {
    MusicusTheme() {
        TracklistUi(
            ArtistViewModel.artistAlbumPreview.tracks.tracks
        )
    }
}

