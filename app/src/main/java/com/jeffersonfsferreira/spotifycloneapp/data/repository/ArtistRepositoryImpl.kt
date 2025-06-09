package com.jeffersonfsferreira.spotifycloneapp.data.repository

import android.util.Log
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.ArtistDao
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toDomain
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.service.SpotifyApiService
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArtistRepositoryImpl(
    private val apiService: SpotifyApiService,
    private val artistDao: ArtistDao
): ArtistRepository {

    override fun getTopArtists(timeRange: String, limit: Int, offset: Int): Flow<List<Artist>> {
        return artistDao.getAllArtists().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchAndCacheTopArtists(timeRange: String, limit: Int, offset: Int) {
        try {
            val response = apiService.getTopArtists(timeRange, limit, offset)
            val artistEntities = response.items.map { it.toEntity() }

            if (offset == 0) {
                artistDao.clearArtists()
            }
            artistDao.insertArtists(artistEntities)
            Log.d("ArtistRepository", "Artists searched from the web and saved in the cache.")
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Error when searching for API artists: ${e.message}", e)
        }
    }

    override suspend fun clearArtistsCache() {
        artistDao.clearArtists()
    }


}