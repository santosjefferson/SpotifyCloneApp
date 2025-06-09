package com.jeffersonfsferreira.spotifycloneapp.core

object Constants {
    const val CLIENT_ID = "494970a4a43844459db144e56a81ab22"
    const val REDIRECT_URI = "com.jeffersonfsferreira.spotifycloneapp://callback"

    // Scopes: Quais permissões seu app precisa?
    // user-top-read: para listar top artistas
    // playlist-read-private: para listar playlists do usuário
    // playlist-modify-public, playlist-modify-private: para criar playlists
    // user-read-private, user-read-email: para ler dados do usuário
    const val SCOPES = "user-top-read,playlist-read-private,playlist-modify-public,playlist-modify-private,user-read-private,user-read-email"
}