package com.jeffersonfsferreira.spotifycloneapp.data.local.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageEntity(
    val url: String,
    val height: Int?,
    val width: Int?
)