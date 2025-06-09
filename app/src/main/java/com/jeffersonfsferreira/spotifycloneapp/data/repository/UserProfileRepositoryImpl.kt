package com.jeffersonfsferreira.spotifycloneapp.data.repository

import android.util.Log
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.UserProfileDao
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toDomain
import com.jeffersonfsferreira.spotifycloneapp.data.mapper.toEntity
import com.jeffersonfsferreira.spotifycloneapp.data.remote.service.SpotifyApiService
import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val apiService: SpotifyApiService,
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {

    override fun getUserProfile(): Flow<UserProfile?> {
        return userProfileDao.getUserProfile().map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun fetchAndCacheUserProfile() {
        try {
            val response = apiService.getCurrentUserProfile()
            userProfileDao.insertUserProfile(response.toEntity())
            Log.d("UserProfileRepo", "Perfil do usuário buscado da rede e salvo no cache.")
        } catch (e: Exception) {
            Log.e("UserProfileRepo", "Erro ao buscar perfil do usuário da API: ${e.message}", e)
        }
    }

    override suspend fun clearUserProfileCache() {
        userProfileDao.clearUserProfile()
    }
}