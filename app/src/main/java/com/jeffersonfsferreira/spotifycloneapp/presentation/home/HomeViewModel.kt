package com.jeffersonfsferreira.spotifycloneapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist
import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetPlaylistsUseCase
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetTopArtistsUseCase
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTopArtistsUseCase: GetTopArtistsUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
): ViewModel() {

    // State of the UI for artists. Represents what the UI should display.
    // We use StateFlow to issue states reactively and efficiently.
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    // Status to indicate whether the screen is loading data.
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Status for error messages (if present)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        // When the ViewModel is initialized, it searches for artists
        loadTopArtists(timeRange = "medium_term", limit = 20, offset = 0)

        loadPlaylists(limit = 20, offset = 0)
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            launch {
                getUserProfileUseCase.invoke()
                    .collect { fetchedProfile ->
                        _userProfile.value = fetchedProfile
                    }
            }

            try {
                getUserProfileUseCase.refreshUserProfile()
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar perfil do usuário: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    /**
     * It loads the top artists, observing changes
     * in the repository (via Room Flow)
     * and also forcing an update from the network.
     */
    private fun loadTopArtists(timeRange: String, limit: Int, offset: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            launch {
                getTopArtistsUseCase.invoke(timeRange, limit, offset)
                    .collect { fetchedArtists ->
                        _artists.value = fetchedArtists
                    }
            }

            try {
                getTopArtistsUseCase.refreshTopArtists(timeRange, limit, offset)
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar artistas da rede: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun loadPlaylists(limit: Int, offset: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            launch {
                getPlaylistsUseCase.invoke(limit, offset)
                    .collect { fetchedPlaylists ->
                        _playlists.value = fetchedPlaylists
                    }
            }

            try {
                getPlaylistsUseCase.refreshPlaylists(limit, offset)
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar playlists da rede: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    /**
     * Try reloading the artists (e.g. pull-to-refresh).
     * Force a network search.
     */
    fun refreshArtists() {
        loadTopArtists(timeRange = "medium_term", limit = 20, offset = 0)
    }

}