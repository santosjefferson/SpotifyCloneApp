package com.jeffersonfsferreira.spotifycloneapp.data.repository

import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {

    /**
     * Gets the user's top artists.
     * First it tries to fetch from the API, saves it in the cache and then returns the data from the cache.
     * If the API fails or there is no connection, it returns the data from the cache.
     * @param timeRange The time period for the top artists (long_term, medium_term, short_term).
     * @param limit The maximum number of artists to return.
     * @param offset The offset for paging.
     * @return An Artist list flow.
     */
    fun getTopArtists(timeRange: String, limit: Int, offset: Int): Flow<List<Artist>>

    /**
     * Searches the API for the top artists and stores them in the local cache.
     */
    suspend fun fetchAndCacheTopArtists(timeRange: String, limit: Int, offset: Int)

    /**
     * Clears the artist cache.
     */
    suspend fun clearArtistsCache()

}