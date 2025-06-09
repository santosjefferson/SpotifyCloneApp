package com.jeffersonfsferreira.spotifycloneapp.data.remote.service

import com.jeffersonfsferreira.spotifycloneapp.data.remote.AlbumDto
import com.jeffersonfsferreira.spotifycloneapp.data.remote.ArtistDto
import com.jeffersonfsferreira.spotifycloneapp.data.remote.PagingDto
import com.jeffersonfsferreira.spotifycloneapp.data.remote.PlaylistDto
import com.jeffersonfsferreira.spotifycloneapp.data.remote.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApiService {

    /*******************************************
       Endpoint to get the user's top artists
       long_term: 1 year of data
       medium_term: 6 months of data
       short_term: 4 weeks of data
     ********************************************/
    @GET("me/top/artists")
    suspend fun getTopArtists(
        @Query("time_range") timeRange: String = "medium_term", // Values: "long_term", "medium_term", "short_term"
        @Query("limit") limit: Int = 20, // Number of artists per page
        @Query("offset") offset: Int = 0 // Offset for pagination

    ): PagingDto<ArtistDto>

    @GET("artists/{id}/albums")
    suspend fun getArtistAlbums(
        @Path("id") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PagingDto<AlbumDto>

    @GET("me/playlists")
    suspend fun getPlaylists(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PagingDto<PlaylistDto>

    @GET("me")
    suspend fun getCurrentUserProfile(): UserProfileDto
}