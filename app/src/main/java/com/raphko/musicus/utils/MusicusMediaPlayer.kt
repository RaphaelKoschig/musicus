package com.raphko.musicus.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.raphko.musicus.model.Track
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Mediaplayer used as a singleton
class MusicusMediaPlayer : MediaPlayer() {
    companion object {
        val shared = MusicusMediaPlayer()
    }

    private val _musicPlaying = MutableStateFlow(false)
    val musicPlaying: StateFlow<Boolean> = _musicPlaying
    private val _trackBeingPlayed = MutableStateFlow<Long>(0)
    val trackBeingPlayed: StateFlow<Long> = _trackBeingPlayed

    fun playTrack(context: Context, track: Track) {
        _musicPlaying.value = false
        _trackBeingPlayed.value = track.id
        try {
            reset()
            setDataSource(context, Uri.parse(track.preview))
            prepare()
            start()
            _musicPlaying.value = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopTrack() {
        _trackBeingPlayed.value = 0
        _musicPlaying.value = false
        reset()
    }
}