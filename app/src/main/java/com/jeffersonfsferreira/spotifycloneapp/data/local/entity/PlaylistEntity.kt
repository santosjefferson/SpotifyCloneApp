package com.jeffersonfsferreira.spotifycloneapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para armazenar dados de playlists localmente.
 */
@Entity(tableName = "playlists")
class PlaylistEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val images: List<ImageEntity>?,
    val ownerName: String,
    val isPublic: Boolean,
    val lastUpdated: Long
)