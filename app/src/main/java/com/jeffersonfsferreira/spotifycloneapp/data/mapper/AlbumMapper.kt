package com.jeffersonfsferreira.spotifycloneapp.data.mapper

import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.AlbumEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.AlbumDto
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Album

fun AlbumDto.toEntity(artistId: String): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        name = this.name,
        albumType = this.albumType,
        releaseDate = this.releaseDate,
        images = this.images?.map { it.toEntity() },
        artistId = artistId,
        artistNames = this.artists.map { it.name },
        lastUpdated = System.currentTimeMillis()
    )
}

fun AlbumEntity.toDomain(): Album {
    return Album(
        id = this.id,
        name = this.name,
        albumType = this.albumType,
        releaseDate = this.releaseDate,
        images = this.images?.map { it.toDomain() },
        artists = emptyList()
    )
}

fun AlbumDto.toDomain(): Album {
    return Album(
        id = this.id,
        name = this.name,
        albumType = this.albumType,
        releaseDate = this.releaseDate,
        images = this.images?.map { it.toDomain() },
        artists = this.artists.map { it.toDomain() }
    )
}