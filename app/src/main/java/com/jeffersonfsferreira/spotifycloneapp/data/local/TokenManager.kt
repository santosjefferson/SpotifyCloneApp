package com.jeffersonfsferreira.spotifycloneapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(context: Context) {

    private val PREFS_NAME = "spotify_prefs"
    private val KEY_ACCESS_TOKEN = "access_token"

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        sharedPrefs.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    fun getAccessToken(): String? {
        return sharedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearAccessToken() {
        sharedPrefs.edit { remove(KEY_ACCESS_TOKEN) }
    }

    fun hasAccessToken(): Boolean = getAccessToken() != null

}