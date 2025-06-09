package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileDto(
    @Json(name = "id") val id: String,
    @Json(name = "display_name") val displayName: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "images") val images: List<ImageDto>?,
    @Json(name = "country") val country: String?,
    @Json(name = "product") val product: String?
)