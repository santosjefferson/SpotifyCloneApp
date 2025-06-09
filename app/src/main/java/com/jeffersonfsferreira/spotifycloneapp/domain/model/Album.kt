package com.jeffersonfsferreira.spotifycloneapp.domain.model

data class Album (
    val id: String,
    val name: String,
    val albumType: String,
    val releaseDate: String,
    val images: List<Image>?,
    val artists: List<Artist>
)