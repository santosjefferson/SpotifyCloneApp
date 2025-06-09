package com.jeffersonfsferreira.spotifycloneapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val albumType: String,
    val releaseDate: String,
    val images: List<ImageEntity>?,
    val artistId: String,
    val artistNames: List<String>?,
    val lastUpdated: Long
)