package com.jeffersonfsferreira.spotifycloneapp.data.repository

import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<UserProfile?>
    suspend fun fetchAndCacheUserProfile()
    suspend fun clearUserProfileCache()
}