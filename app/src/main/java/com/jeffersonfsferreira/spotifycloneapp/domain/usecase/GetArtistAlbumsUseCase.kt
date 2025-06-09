package com.jeffersonfsferreira.spotifycloneapp.domain.usecase

import com.jeffersonfsferreira.spotifycloneapp.data.repository.AlbumRepository
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetArtistAlbumsUseCase(private val albumRepository: AlbumRepository) {

    operator fun invoke(artistId: String, limit: Int, offset: Int): Flow<List<Album>> {
        return albumRepository.getAlbumsForArtist(artistId, limit, offset)
            .distinctUntilChanged()
    }

    suspend fun refreshArtistAlbums(artistId: String, limit: Int, offset: Int) {
        albumRepository.fetchAndCacheAlbumsForArtist(artistId, limit, offset)
    }
}