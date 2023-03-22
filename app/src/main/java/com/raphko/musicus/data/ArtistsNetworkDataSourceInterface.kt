package com.raphko.musicus.data

import com.raphko.musicus.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistsNetworkDataSourceInterface {

    @GET("/search/artist")
    suspend fun searchArtists(@Query("q") searchTerm: String): ArtistList

    @GET("/artist/{artistId}")
    suspend fun getArtist(@Path("artistId") artistId: Long): Artist

    @GET("/artist/{artistId}/albums")
    suspend fun getArtistAlbums(@Path("artistId") artistId: Long): AlbumList

    @GET("/album/{albumId}/tracks")
    suspend fun getAlbumTracks(@Path("albumId") albumId: Long): TrackList
}