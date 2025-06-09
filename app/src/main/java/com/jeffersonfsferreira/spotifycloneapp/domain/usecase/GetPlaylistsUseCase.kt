package com.jeffersonfsferreira.spotifycloneapp.domain.usecase

import com.jeffersonfsferreira.spotifycloneapp.data.repository.PlaylistRepository
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetPlaylistsUseCase(private val playlistRepository: PlaylistRepository) {

    operator fun invoke(limit: Int, offset: Int): Flow<List<Playlist>> {
        return playlistRepository.getPlaylists(limit, offset)
            .distinctUntilChanged()
    }

    suspend fun refreshPlaylists(limit: Int, offset: Int) {
        playlistRepository.fetchAndCachePlaylists(limit, offset)
    }
}