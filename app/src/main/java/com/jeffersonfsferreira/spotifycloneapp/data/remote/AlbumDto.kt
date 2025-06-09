package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumDto (
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "album_type") val albumType: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "images") val images: List<ImageDto>?,
    @Json(name = "artists") val artists: List<ArtistDto>
)