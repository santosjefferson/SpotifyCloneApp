package com.jeffersonfsferreira.spotifycloneapp.data.mapper

import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.UserProfileEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.UserProfileDto
import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile

fun UserProfileDto.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        images = this.images?.map { it.toEntity() },
        country = this.country,
        product = this.product,
        lastUpdated = System.currentTimeMillis()
    )
}

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        images = this.images?.map { it.toDomain() },
        country = this.country,
        product = this.product
    )
}

fun UserProfileDto.toDomain(): UserProfile {
    return UserProfile(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        images = this.images?.map { it.toDomain() },
        country = this.country,
        product = this.product
    )
}