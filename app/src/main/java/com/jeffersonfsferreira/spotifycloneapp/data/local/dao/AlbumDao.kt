package com.jeffersonfsferreira.spotifycloneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Query("SELECT * FROM albums WHERE artistId = :artistId ORDER BY releaseDate DESC")
    fun getAlbumsForArtist(artistId: String): Flow<List<AlbumEntity>>

    @Query("DELETE FROM albums WHERE artistId = :artistId")
    suspend fun clearAlbumsForArtist(artistId: String)
}