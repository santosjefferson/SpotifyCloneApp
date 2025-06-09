package com.jeffersonfsferreira.spotifycloneapp.data.repository

import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylists(limit: Int, offset: Int): Flow<List<Playlist>>
    suspend fun fetchAndCachePlaylists(limit: Int, offset: Int)
    suspend fun clearPlaylistsCache()
}