package com.jeffersonfsferreira.spotifycloneapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Precisaremos de um UserDto simples para o dono da playlist.
// Você pode criar um arquivo separado UserDto.kt para isso ou colocar aqui por simplicidade no MVP.
// Vamos criar um UserDto simples aqui para o MVP, e depois teremos um mais completo para "dados do usuário".
@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "display_name") val displayName: String?,
    @Json(name = "id") val id: String
)