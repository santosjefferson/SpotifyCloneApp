package com.jeffersonfsferreira.spotifycloneapp.domain.usecase

import com.jeffersonfsferreira.spotifycloneapp.data.repository.ArtistRepository
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetTopArtistsUseCase(private val artistRepository: ArtistRepository) {

    operator fun invoke(timeRange: String, limit: Int, offset: Int): Flow<List<Artist>> {
        return artistRepository.getTopArtists(timeRange, limit, offset)
            .distinctUntilChanged()
    }

    suspend fun refreshTopArtists(timeRange: String, limit: Int, offset: Int) {
        artistRepository.fetchAndCacheTopArtists(timeRange, limit, offset)
    }
}