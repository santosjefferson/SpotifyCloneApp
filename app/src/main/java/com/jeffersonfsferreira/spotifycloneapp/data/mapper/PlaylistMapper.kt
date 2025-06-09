package com.jeffersonfsferreira.spotifycloneapp.data.mapper

import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.PlaylistEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.PlaylistDto
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist

fun PlaylistDto.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        images = this.images?.map { it.toEntity() },
        ownerName = this.owner.displayName ?: this.owner.id, // Usa o nome ou ID do dono
        isPublic = this.public,
        lastUpdated = System.currentTimeMillis()
    )
}

fun PlaylistEntity.toDomain(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        images = this.images?.map { it.toDomain() },
        ownerName = this.ownerName,
        isPublic = this.isPublic
    )
}

fun PlaylistDto.toDomain(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        images = this.images?.map { it.toDomain() },
        ownerName = this.owner.displayName ?: this.owner.id,
        isPublic = this.public
    )
}