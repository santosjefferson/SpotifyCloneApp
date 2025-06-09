package com.jeffersonfsferreira.spotifycloneapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: String,
    val displayName: String?,
    val email: String?,
    val images: List<ImageEntity>?,
    val country: String?,
    val product: String?,
    val lastUpdated: Long
)