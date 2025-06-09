package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Modelo para um objeto de playlist da API do Spotify.
 * Simplificado para o MVP.
 * @param id O ID do Spotify para a playlist.
 * @param name O nome da playlist.
 * @param description A descrição da playlist.
 * @param images Uma lista de objetos de imagem para a playlist.
 * @param owner Informações sobre o criador/dono da playlist.
 * @param public Indica se a playlist é pública.
 */
@JsonClass(generateAdapter = true)
data class PlaylistDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "images") val images: List<ImageDto>?,
    @Json(name = "owner") val owner: UserDto, // UserDto para o dono da playlist (simplificado)
    @Json(name = "public") val public: Boolean
)