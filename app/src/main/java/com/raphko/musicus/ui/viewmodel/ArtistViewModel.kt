package com.raphko.musicus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphko.musicus.data.ArtistsRepository
import com.raphko.musicus.model.Album
import com.raphko.musicus.model.Artist
import com.raphko.musicus.model.Track
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(
    private val artistsRepository: ArtistsRepository = ArtistsRepository()
) : ViewModel() {
    private val _artist = MutableStateFlow(
        Artist(
            id = 0,
            name = "",
            pictureSmall = "",
            pictureMedium = "",
            pictureBig = ""
        )
    )
    val artist: StateFlow<Artist> = _artist
    private val _firstAlbum = MutableStateFlow(
        Album(
            id = 0,
            title = "No Album",
            coverMedium = "",
            releaseDate = ""
        )
    )
    val firstAlbum: StateFlow<Album> = _firstAlbum
    private val _trackList = MutableStateFlow(emptyList<Track>())
    val trackList: StateFlow<List<Track>> = _trackList
    private val _artistLoaded = MutableStateFlow(false)
    val artistLoaded: StateFlow<Boolean> = _artistLoaded
    private val _albumLoaded = MutableStateFlow(false)
    val albumLoaded: StateFlow<Boolean> = _albumLoaded
    private val _noAlbum = MutableStateFlow(false)
    val noAlbum: StateFlow<Boolean> = _noAlbum
    private val _tracksLoaded = MutableStateFlow(false)
    val tracksLoaded: StateFlow<Boolean> = _tracksLoaded
    private val _noTrack = MutableStateFlow(false)
    val noTrack: StateFlow<Boolean> = _noTrack

    // Use coroutine to make all necessary requests to get artist and album infos
    fun loadArtistAndAlbum(artistId: Long) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val artist = artistsRepository.getArtist(artistId)) {
                    is Artist -> {
                        _artist.value = artist
                    }
                    // TODO Should add a way yo display error, etc
                    else -> {
                        println("No data for this artist")
                    }
                }
                _artistLoaded.value = true
            } catch (exception: IOException) {
                println("Failed to get artist")
            }

            try {
                when (val album = artistsRepository.getArtistFirstAlbum(artistId)) {
                    is Album -> {
                        _firstAlbum.value = album
                    }
                    // TODO Should add a way yo display error, etc
                    else -> {
                        _noAlbum.value = true
                        println("No data for this album")
                    }
                }
                _albumLoaded.value = true
            } catch (exception: IOException) {
                println("Failed to get artist")
            }

            try {
                when (val tracks = artistsRepository.getAlbumTracks(_firstAlbum.value)) {
                    is List<Track> -> {
                        _trackList.value = tracks
                    }
                    // TODO Should add a way yo display error, etc
                    else -> {
                        println("No track for this album")
                        _noTrack.value = true
                    }
                }
                _tracksLoaded.value = true
            } catch (exception: IOException) {
                println("Failed to get artist")
            }

        }
    }

    // Reset the state in order to reloaded it when navigate again
    fun reset() {
        _artistLoaded.value = false
        _albumLoaded.value = false
        _noAlbum.value = false
        _tracksLoaded.value = false
        _noTrack.value = false
    }

    // For preview
    companion object {

        val artistPreview: Artist =
            Artist(
                id = 27,
                name = "Daft Punk",
                pictureSmall = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg",
                pictureMedium = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg",
                pictureBig = "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg"
            )

        val artistAlbumPreview: Album =
            Album(
                id = 320098917,
                title = "The Eminem Show (Expanded Edition)",
                coverMedium = "https://e-cdns-images.dzcdn.net/images/cover/d6e14fe8e855c20db60b31a7b42eb007/250x250-000000-80-0-0.jpg",
                releaseDate = "2022-05-26",
                tracks = arrayListOf(
                    Track(
                        id = 1757177167,
                        title = "Curtains Up (Skit)",
                        preview = "https://cdns-preview-a.dzcdn.net/stream/c-aae464c0d9b62db5f5b6dffcd5ce74f3-4.mp3"
                    ),
                    Track(
                        id = 1757177177,
                        title = "White America",
                        preview = "https://cdns-preview-8.dzcdn.net/stream/c-883314d5c6af0e05da8c801f0d815597-4.mp3"
                    ),
                    Track(
                        id = 1757177187,
                        title = "Business",
                        preview = "https://cdns-preview-e.dzcdn.net/stream/c-e15f393762853e10d6c12e7be3594fec-4.mp3"
                    )
                )
            )
    }

    fun loadAlbumPreview() {
        _artist.value = artistPreview
        _firstAlbum.value = artistAlbumPreview
        _artistLoaded.value = true
        _albumLoaded.value = true
        _tracksLoaded.value = true
    }
}