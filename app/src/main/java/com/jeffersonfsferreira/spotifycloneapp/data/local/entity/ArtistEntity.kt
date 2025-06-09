package com.jeffersonfsferreira.spotifycloneapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val genres: List<String>?,
    val images: List<ImageEntity>?,
    val lastUpdated: Long
)