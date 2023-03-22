package com.raphko.musicus.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphko.musicus.data.ArtistsRepository
import com.raphko.musicus.model.ArtistMinimal
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistListViewModel(
    private val artistsRepository: ArtistsRepository = ArtistsRepository()
) : ViewModel() {
    private val _artistsSearchList = MutableStateFlow(emptyList<ArtistMinimal>())
    val artistsSearchList: StateFlow<List<ArtistMinimal>> = _artistsSearchList
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _launchedFirstSearch = MutableStateFlow(false)
    val launchedFirstSearch: StateFlow<Boolean> = _launchedFirstSearch

    private val errorHandler =
        CoroutineExceptionHandler { _, exception -> exception.printStackTrace() }

    fun storeArtistsListInCache() {
        artistsRepository.storeListArtistsInCache(artistsSearchList.value)
    }

    fun checkArtistsListCache() {
        val artistsListCache = artistsRepository.getListArtistsInCache()
        if (!artistsListCache.isEmpty()) {
            _launchedFirstSearch.value = true
            _artistsSearchList.value = artistsListCache
        }
    }

    // Use coroutine to make request to search artists
    fun searchArtists(searchTerm: String) {
        viewModelScope.launch(errorHandler) {
            _launchedFirstSearch.value = true
            _isLoading.value = true
            try {
                val artistList = artistsRepository.searchArtists(searchTerm)
                Log.v("ArtistListModel", "searchArtists: " + artistList.artists)
                _artistsSearchList.value = artistList.artists
                _isLoading.value = false
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    companion object {
        val artistSeachListPreview: List<ArtistMinimal> =
            listOf(
                ArtistMinimal(
                    id = 11,
                    name = "Raph",
                    "https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg"
                ),
                ArtistMinimal(
                    id = 33432,
                    name = "Jungle",
                    "https://e-cdns-images.dzcdn.net/images/artist/1d22fb55b9a7a3e1928e2bed5af7a86c/250x250-000000-80-0-0.jpg"
                )
            )
    }

    fun loadArtistsListPreview() {
        _artistsSearchList.value = artistSeachListPreview
    }
}