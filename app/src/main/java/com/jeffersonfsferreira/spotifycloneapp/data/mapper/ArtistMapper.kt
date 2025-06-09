package com.jeffersonfsferreira.spotifycloneapp.data.mapper

import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.ArtistEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.ArtistDto
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist

fun ArtistDto.toEntity(): ArtistEntity {
    return ArtistEntity(
        id = this.id,
        name = this.name,
        genres = this.genres,
        images = this.images?.map { it.toEntity() },
        lastUpdated = System.currentTimeMillis()
    )
}

fun ArtistEntity.toDomain(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        genres = this.genres,
        images = this.images?.map { it.toDomain() }
    )
}

fun ArtistDto.toDomain(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        genres = this.genres,
        images = this.images?.map { it.toDomain() }
    )
}