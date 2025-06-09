package com.jeffersonfsferreira.spotifycloneapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeffersonfsferreira.spotifycloneapp.data.local.converter.ListConverters
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.AlbumDao
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.ArtistDao
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.PlaylistDao
import com.jeffersonfsferreira.spotifycloneapp.data.local.dao.UserProfileDao
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.AlbumEntity
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.ArtistEntity
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.PlaylistEntity
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.UserProfileEntity

@Database(entities = [
    ArtistEntity::class,
    AlbumEntity::class,
    PlaylistEntity::class,
    UserProfileEntity::class], version = 4, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun albumDao(): AlbumDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun userProfileDao(): UserProfileDao
}