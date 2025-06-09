package com.jeffersonfsferreira.spotifycloneapp.presentation.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Album
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetArtistAlbumsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel (
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadArtistAlbums(artistId: String, limit: Int, offset: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            launch {
                getArtistAlbumsUseCase.invoke(artistId, limit, offset)
                    .collect { fetchedAlbums ->
                        _albums.value = fetchedAlbums
                    }
            }

            try {
                getArtistAlbumsUseCase.refreshArtistAlbums(artistId, limit, offset)
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao carregar álbuns da rede: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}