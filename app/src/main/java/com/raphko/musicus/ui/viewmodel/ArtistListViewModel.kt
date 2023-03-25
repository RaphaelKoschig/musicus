package com.raphko.musicus.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphko.musicus.data.ArtistsRepository
import com.raphko.musicus.model.ArtistMinimal
import androidx.lifecycle.SavedStateHandle
import com.raphko.musicus.ui.viewmodel.ArtistListViewModel.Companion.artistSeachListPreview
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class ArtistListViewModelAbstract: ViewModel() {
    abstract val artistsSearchList: StateFlow<List<ArtistMinimal>>
    abstract val isLoading: StateFlow<Boolean>
    abstract val launchedFirstSearch: StateFlow<Boolean>

    abstract fun searchArtists(searchTerm: String)
    abstract fun storeArtistsListInCache()
    abstract fun checkArtistsListCache()
}

class ArtistListViewModel(
    private val stateHandle: SavedStateHandle
) : ArtistListViewModelAbstract() {
    private val artistsRepository: ArtistsRepository = ArtistsRepository()
    private val errorHandler =
        CoroutineExceptionHandler { _, exception -> exception.printStackTrace() }
    private var useCache: Boolean = true

    // States Flow
    private val _artistsSearchList = MutableStateFlow(emptyList<ArtistMinimal>())
    override val artistsSearchList: StateFlow<List<ArtistMinimal>> = _artistsSearchList
    private val _isLoading = MutableStateFlow(true)
    override val isLoading: StateFlow<Boolean> = _isLoading
    private val _launchedFirstSearch = MutableStateFlow(false)
    override val launchedFirstSearch: StateFlow<Boolean> = _launchedFirstSearch

    override fun storeArtistsListInCache() {
        stateHandle[ARTISTS_LIST] = artistsSearchList.value
    }

    override fun checkArtistsListCache() {
        val storeArtistsList = stateHandle.get<List<ArtistMinimal>?>(ARTISTS_LIST)
        if (storeArtistsList != null) {
            if (!storeArtistsList.isEmpty() && useCache) {
                _launchedFirstSearch.value = true
                _artistsSearchList.value = storeArtistsList
            }
        }
    }

    // Use coroutine to make request to search artists
    override fun searchArtists(searchTerm: String) {
        viewModelScope.launch(errorHandler) {
            useCache = false
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
        const val ARTISTS_LIST = "artistsList"

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
}

class ArtistListViewModelPreview(
    override val artistsSearchList: StateFlow<List<ArtistMinimal>> = MutableStateFlow(emptyList<ArtistMinimal>()),
    override val isLoading: StateFlow<Boolean> = MutableStateFlow(true),
    override val launchedFirstSearch: StateFlow<Boolean> = MutableStateFlow(false)
) : ArtistListViewModelAbstract() {

    override fun storeArtistsListInCache() {}

    override fun checkArtistsListCache() {}

    override fun searchArtists(searchTerm: String) {}
}
