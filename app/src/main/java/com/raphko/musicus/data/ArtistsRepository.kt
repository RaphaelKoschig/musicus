package com.raphko.musicus.data

import com.raphko.musicus.model.Album
import com.raphko.musicus.model.Artist
import com.raphko.musicus.model.ArtistMinimal
import com.raphko.musicus.model.Track

// Manage data actions for artists
class ArtistsRepository(
    private val artistRemoteDataSource: ArtistsNetworkDataSource = ArtistsNetworkDataSource()
) {
    suspend fun searchArtists(searchTerm: String): List<ArtistMinimal>? =
        artistRemoteDataSource.searchArtists(searchTerm)

    suspend fun getArtist(artistId: Long): Artist? =
        artistRemoteDataSource.getArtist(artistId)

    suspend fun getArtistFirstAlbum(artistId: Long): Album? =
        artistRemoteDataSource.getArtistFirstAlbum(artistId)

    suspend fun getAlbumTracks(album: Album): List<Track>? =
        artistRemoteDataSource.getAlbumTracks(album)
}