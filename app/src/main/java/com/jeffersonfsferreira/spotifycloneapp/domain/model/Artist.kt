package com.jeffersonfsferreira.spotifycloneapp.domain.model

data class Artist (
    val id: String,
    val name: String,
    val genres: List<String>?,
    val images: List<Image>?
)