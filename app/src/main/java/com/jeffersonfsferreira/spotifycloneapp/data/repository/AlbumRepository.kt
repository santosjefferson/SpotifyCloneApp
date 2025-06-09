package com.jeffersonfsferreira.spotifycloneapp.data.repository

import com.jeffersonfsferreira.spotifycloneapp.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAlbumsForArtist(artistId: String, limit: Int, offset: Int): Flow<List<Album>>
    suspend fun fetchAndCacheAlbumsForArtist(artistId: String, limit: Int, offset: Int)
}