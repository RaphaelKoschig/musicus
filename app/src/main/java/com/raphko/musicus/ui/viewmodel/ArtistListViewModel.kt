package com.raphko.musicus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphko.musicus.data.ArtistsRepository
import com.raphko.musicus.model.ArtistMinimal
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
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
    private val _searchTerm = MutableStateFlow("")
    val searchTerm: StateFlow<String> = _searchTerm

    // Store changeTerme in state
    fun changeSearchTerm(searchTerm: String) {
        _searchTerm.value = searchTerm
    }

    // Use coroutine to make request to search artists
    fun searchArtists(searchTerm: String) {
        _launchedFirstSearch.value = true
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val result = artistsRepository.searchArtists(searchTerm)) {
                    is List<ArtistMinimal> -> _artistsSearchList.value = result
                    // TODO Should add a way yo display error, etc
                    else -> println("No data for this search term")
                }
                _isLoading.value = false
            } catch (exception: IOException) {
                println("Failed to search artist")
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