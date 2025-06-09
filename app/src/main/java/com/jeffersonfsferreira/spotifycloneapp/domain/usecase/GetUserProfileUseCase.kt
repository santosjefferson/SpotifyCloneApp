package com.jeffersonfsferreira.spotifycloneapp.domain.usecase

import com.jeffersonfsferreira.spotifycloneapp.data.repository.UserProfileRepository
import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetUserProfileUseCase(private val userProfileRepository: UserProfileRepository) {

    operator fun invoke(): Flow<UserProfile?> {
        return userProfileRepository.getUserProfile()
            .distinctUntilChanged()
    }

    suspend fun refreshUserProfile() {
        userProfileRepository.fetchAndCacheUserProfile()
    }
}