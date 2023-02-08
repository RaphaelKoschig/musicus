package com.raphko.musicus

import android.media.AudioAttributes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.raphko.musicus.ui.MusicusNavigation
import com.raphko.musicus.ui.PlayerBoxUi
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.utils.MusicusMediaPlayer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Preparation of mediaplayer
        MusicusMediaPlayer.shared.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        // Listen to music ending in mediaplayer, if so it hides the stop button
        MusicusMediaPlayer.shared.setOnCompletionListener {
            MusicusMediaPlayer.shared.stopTrack()
        }

        // Main content, here we can show or hide the playerBox with state of musicPlaying
        setContent {
            val musicPlaying by MusicusMediaPlayer.shared.musicPlaying.collectAsState()
            MusicusTheme {
                MusicusNavigation()
                if (musicPlaying) {
                    PlayerBoxUi()
                }
            }
        }
    }
}