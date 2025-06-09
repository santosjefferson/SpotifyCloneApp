package com.jeffersonfsferreira.spotifycloneapp.data.mapper

import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.ImageEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.ImageDto
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Image

fun ImageDto.toEntity(): ImageEntity {
    return ImageEntity(
        url = this.url,
        height = this.height,
        width = this.width
    )
}

fun ImageEntity.toDomain(): Image {
    return Image(
        url = this.url,
        height = this.height,
        width = this.width
    )
}

fun ImageDto.toDomain(): Image {
    return Image(
        url = this.url,
        height = this.height,
        width = this.width
    )
}