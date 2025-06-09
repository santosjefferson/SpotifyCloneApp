package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtistDto(

    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "genres") val genres: List<String>?,
    @Json(name = "images") val images: List<ImageDto>?
)