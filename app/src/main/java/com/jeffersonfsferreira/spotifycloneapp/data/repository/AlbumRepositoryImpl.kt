package com.jeffersonfsferreira.spotifycloneapp.data.repository

import android.util.Log
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.AlbumDao
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toDomain
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.service.SpotifyApiService
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepositoryImpl(
    private val apiService: SpotifyApiService,
    private val albumDao: AlbumDao
) : AlbumRepository {

    override fun getAlbumsForArtist(artistId: String, limit: Int, offset: Int): Flow<List<Album>> {
        return albumDao.getAlbumsForArtist(artistId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchAndCacheAlbumsForArtist(artistId: String, limit: Int, offset: Int) {
        try {
            val response = apiService.getArtistAlbums(artistId, limit = limit, offset = offset)
            val albumEntities = response.items.map { it.toEntity(artistId) }

            if (offset == 0) {
                albumDao.clearAlbumsForArtist(artistId)
            }
            albumDao.insertAlbums(albumEntities)
            Log.d("AlbumRepository", "Álbuns de artista $artistId buscados da rede e salvos no cache.")
        } catch (e: Exception) {
            Log.e("AlbumRepository", "Erro ao buscar álbuns para o artista $artistId da API: ${e.message}", e)
        }
    }
}