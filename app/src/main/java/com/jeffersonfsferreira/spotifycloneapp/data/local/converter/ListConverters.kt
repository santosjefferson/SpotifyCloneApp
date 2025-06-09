package com.jeffersonfsferreira.spotifycloneapp.data.local.converter

import androidx.room.TypeConverter
import com.jeffersonfsferreira.spotifycloneapp.data.local.entity.ImageEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ListConverters {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // --- Converters from List<String> (Genres) ---
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(value)
    }

    // --- Converters from List<ImageEntity> (Images) ---
    @TypeConverter
    fun fromImageEntityList(value: List<ImageEntity>?): String? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, ImageEntity::class.java)
        val adapter = moshi.adapter<List<ImageEntity>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toImageEntityList(value: String?): List<ImageEntity>? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, ImageEntity::class.java)
        val adapter = moshi.adapter<List<ImageEntity>>(type)
        return adapter.fromJson(value)
    }
}