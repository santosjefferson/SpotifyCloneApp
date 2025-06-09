package com.jeffersonfsferreira.spotifycloneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.ArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    @Query("SELECT * FROM artists ORDER BY lastUpdated DESC")
    fun getAllArtists(): Flow<List<ArtistEntity>>

    @Query("DELETE FROM artists")
    suspend fun clearArtists()
}