package com.jeffersonfsferreira.spotifycloneapp.data.repository

import android.util.Log
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.PlaylistDao
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toDomain
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.service.SpotifyApiService
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepositoryImpl(
    private val apiService: SpotifyApiService,
    private val playlistDao: PlaylistDao
) : PlaylistRepository {

    override fun getPlaylists(limit: Int, offset: Int): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchAndCachePlaylists(limit: Int, offset: Int) {
        try {
            val response = apiService.getPlaylists(limit, offset)
            val playlistEntities = response.items.map { it.toEntity() }

            if (offset == 0) {
                playlistDao.clearPlaylists()
            }
            playlistDao.insertPlaylists(playlistEntities)
            Log.d("PlaylistRepo", "Playlists buscadas da rede e salvas no cache. Total: ${playlistEntities.size}")
        } catch (e: Exception) {
            Log.e("PlaylistRepo", "Erro ao buscar playlists da API: ${e.message}", e)
        }
    }

    override suspend fun clearPlaylistsCache() {
        playlistDao.clearPlaylists()
    }
}