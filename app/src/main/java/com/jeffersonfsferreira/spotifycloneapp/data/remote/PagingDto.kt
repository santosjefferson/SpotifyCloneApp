package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagingDto<T> (

    @Json(name = "items") val items: List<T>,
    @Json(name = "total") val total: Int,
    @Json(name = "limit") val limit: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?
)