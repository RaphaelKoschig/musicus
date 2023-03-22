package com.raphko.musicus.data

import com.raphko.musicus.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Manage data actions for artists
class ArtistsRepository() {
    private var artistRemoteDataSource: ArtistsNetworkDataSourceInterface
    private var listArtistsCache: List<ArtistMinimal> = ArrayList<ArtistMinimal>()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.deezer.com")
            .client(client)
            .build()
        artistRemoteDataSource = retrofit.create(ArtistsNetworkDataSourceInterface::class.java)
    }

    suspend fun searchArtists(searchTerm: String): ArtistList {
        return withContext(Dispatchers.IO) {
            artistRemoteDataSource.searchArtists(searchTerm)
        }
    }

    suspend fun getArtist(artistId: Long): Artist {
        return withContext(Dispatchers.IO) {
            artistRemoteDataSource.getArtist(artistId)
        }
    }

    suspend fun getArtistFirstAlbum(artistId: Long): Album {
        return withContext(Dispatchers.IO) {
            artistRemoteDataSource.getArtistAlbums(artistId).albums[0]
        }
    }

    suspend fun getAlbumTracks(album: Album): TrackList {
        return withContext(Dispatchers.IO) {
            artistRemoteDataSource.getAlbumTracks(album.id)
        }
    }

    fun storeListArtistsInCache(listArtists: List<ArtistMinimal>) {
        listArtistsCache = listArtists
    }

    fun getListArtistsInCache(): List<ArtistMinimal> {
        return listArtistsCache
    }
}