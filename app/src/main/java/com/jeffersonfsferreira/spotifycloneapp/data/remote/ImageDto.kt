package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "url") val url: String,
    @Json(name = "height") val height: Int?,
    @Json(name = "width") val width: Int?
)