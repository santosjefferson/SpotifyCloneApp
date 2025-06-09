package com.jeffersonfsferreira.spotifycloneapp.domain.model

data class UserProfile(
    val id: String,
    val displayName: String?,
    val email: String?,
    val images: List<Image>?,
    val country: String?,
    val product: String?
)