package com.raphko.musicus.data

import com.raphko.musicus.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

// Manage endpoints of Deezer API
class DeezerApi {
    private val urlApi: String = BuildConfig.DEEZER_API
    private val client: HttpClient = HttpClient(CIO)

    suspend fun getSearchArtists(searchTerm: String): HttpResponse {
        return client.get("${urlApi}/search/artist?q=$searchTerm")
    }

    suspend fun getArtist(artistId: Long): HttpResponse {
        return client.get("${urlApi}/artist/$artistId")
    }

    suspend fun getArtistAlbums(artistId: Long): HttpResponse {
        return client.get("${urlApi}/artist/$artistId/albums")
    }

    suspend fun getAlbumTracks(albumId: Long): HttpResponse {
        return client.get("${urlApi}/album/$albumId/tracks")
    }
}