package com.jeffersonfsferreira.spotifycloneapp.domain.model

/**
 * Modelo de domínio para uma playlist.
 */
data class Playlist(
    val id: String,
    val name: String,
    val description: String?,
    val images: List<Image>?,
    val ownerName: String, // Nome do dono
    val isPublic: Boolean
)