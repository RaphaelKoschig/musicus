package com.raphko.musicus.data

import com.raphko.musicus.model.Album
import com.raphko.musicus.model.Artist
import com.raphko.musicus.model.ArtistMinimal
import com.raphko.musicus.model.Track
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import java.net.URLEncoder

// To manage resources coming from remote
class ArtistsNetworkDataSource(private val deezerApi: DeezerApi = DeezerApi()) {

    suspend fun searchArtists(
        searchTerm: String
    ): List<ArtistMinimal>? {
        return withContext(Dispatchers.IO) {
            val searchTermEncoded = URLEncoder.encode(searchTerm, "UTF-8")
            val rawData: JsonElement? =
                Json.parseToJsonElement(
                    deezerApi.getSearchArtists(searchTermEncoded)
                        .bodyAsText()
                ).jsonObject["data"]
            val artistSearchList: List<ArtistMinimal>
            if (rawData != null) {
                // TODO manage automatic mapping with serialization
                artistSearchList = rawData.jsonArray.map {
                    ArtistMinimal(
                        id = it.jsonObject["id"].toString().toLong(),
                        name = it.jsonObject.get("name")?.jsonPrimitive?.content ?: "No name",
                        pictureMedium = it.jsonObject.get("picture_medium")?.jsonPrimitive?.content
                            ?: ""
                    )
                }
                println(artistSearchList)
                return@withContext artistSearchList
            } else {
                return@withContext null
            }
        }
    }

    suspend fun getArtist(
        artistId: Long
    ): Artist? {
        return withContext(Dispatchers.IO) {
            val rawData: JsonElement =
                Json.parseToJsonElement(
                    deezerApi.getArtist(artistId)
                        .bodyAsText()
                )
            if (rawData.jsonObject.get("error") != null) {
                return@withContext null
            } else {
                // TODO manage automatic mapping with serialization
                val artist = Artist(
                    id = rawData.jsonObject["id"].toString().toLong(),
                    name = rawData.jsonObject.get("name")?.jsonPrimitive?.content
                        ?: "No name",
                    pictureSmall = rawData.jsonObject.get("picture_small")?.jsonPrimitive?.content
                        ?: "",
                    pictureMedium = rawData.jsonObject.get("picture_medium")?.jsonPrimitive?.content
                        ?: "",
                    pictureBig = rawData.jsonObject.get("picture_big")?.jsonPrimitive?.content
                        ?: ""
                )
                println(artist)
                return@withContext artist
            }
        }
    }

    suspend fun getArtistFirstAlbum(
        artistId: Long
    ): Album? {
        return withContext(Dispatchers.IO) {
            val rawData: JsonElement? =
                Json.parseToJsonElement(
                    deezerApi.getArtistAlbums(artistId)
                        .bodyAsText()
                ).jsonObject["data"]
            // TODO manage exceptions and errors
            if (rawData != null && !rawData.jsonArray.isEmpty()) {
                // TODO manage automatic mapping with serialization
                val firstAlbumElement: JsonElement = rawData.jsonArray.get(0)
                val firstAlbum = Album(
                    id = firstAlbumElement.jsonObject["id"].toString().toLong(),
                    title = firstAlbumElement.jsonObject.get("title")?.jsonPrimitive?.content
                        ?: "No title",
                    coverMedium = firstAlbumElement.jsonObject.get("cover_medium")?.jsonPrimitive?.content
                        ?: "",
                    releaseDate = firstAlbumElement.jsonObject.get("release_date")?.jsonPrimitive?.content
                        ?: "",
                )
                println(firstAlbum)
                return@withContext firstAlbum
            } else {
                return@withContext null
            }
        }
    }

    suspend fun getAlbumTracks(
        album: Album
    ): List<Track>? {
        return withContext(Dispatchers.IO) {
            val rawData: JsonElement? =
                Json.parseToJsonElement(
                    deezerApi.getAlbumTracks(album.id)
                        .bodyAsText()
                ).jsonObject["data"]
            val trackList: List<Track>
            // TODO manage exceptions and errors
            if (rawData != null) {
                // TODO manage automatic mapping with serialization
                trackList = rawData.jsonArray.map {
                    Track(
                        id = it.jsonObject["id"].toString().toLong(),
                        title = it.jsonObject.get("title")?.jsonPrimitive?.content ?: "No name",
                        preview = it.jsonObject.get("preview")?.jsonPrimitive?.content ?: ""
                    )
                }
                return@withContext trackList
            } else {
                return@withContext null
            }
        }
    }
}