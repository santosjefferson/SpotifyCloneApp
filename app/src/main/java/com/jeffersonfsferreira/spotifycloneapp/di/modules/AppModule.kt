package com.jeffersonfsferreira.spotifycloneapp.di.modules

import androidx.room.Room
import com.jeffersonfsferreira.spotifycloneapp.data.local.TokenManager
import com.jeffersonfsferreira.spotifycloneapp.data.local.db.AppDatabase
import com.jeffersonfsferreira.spotifycloneapp.data.remote.interceptor.AuthInterceptor
import com.jeffersonfsferreira.spotifycloneapp.data.remote.service.SpotifyApiService
import com.jeffersonfsferreira.spotifycloneapp.data.repository.AlbumRepository
import com.jeffersonfsferreira.spotifycloneapp.data.repository.AlbumRepositoryImpl
import com.jeffersonfsferreira.spotifycloneapp.data.repository.ArtistRepository
import com.jeffersonfsferreira.spotifycloneapp.data.repository.ArtistRepositoryImpl
import com.jeffersonfsferreira.spotifycloneapp.data.repository.PlaylistRepository
import com.jeffersonfsferreira.spotifycloneapp.data.repository.PlaylistRepositoryImpl
import com.jeffersonfsferreira.spotifycloneapp.data.repository.UserProfileRepository
import com.jeffersonfsferreira.spotifycloneapp.data.repository.UserProfileRepositoryImpl
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetArtistAlbumsUseCase
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetPlaylistsUseCase
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetTopArtistsUseCase
import com.jeffersonfsferreira.spotifycloneapp.domain.usecase.GetUserProfileUseCase
import com.jeffersonfsferreira.spotifycloneapp.presentation.albums.AlbumsViewModel
import com.jeffersonfsferreira.spotifycloneapp.presentation.home.HomeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.spotify.com/v1/"

val appModule = module {
    single { TokenManager(androidContext()) }

    // HttpLoggingInterceptor for network logs (for debugging)
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Adds the authorization token
    single { AuthInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Moshi for JSON serialization/deserialization
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(SpotifyApiService::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "spotify_clone_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().artistDao() }
    single { get<AppDatabase>().albumDao() }
    single { get<AppDatabase>().playlistDao() }
    single { get<AppDatabase>().userProfileDao() }

    // --- DEPENDÊNCIAS DOS REPOSITÓRIOS ---
    single<ArtistRepository> { ArtistRepositoryImpl(get(), get()) }
    single<AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
    single<PlaylistRepository> { PlaylistRepositoryImpl(get(), get()) }
    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }

    // --- DEPENDÊNCIAS DE DOMÍNIO E APRESENTAÇÃO ---
    factory { GetTopArtistsUseCase(get()) }
    factory { GetArtistAlbumsUseCase(get()) }
    factory { GetPlaylistsUseCase(get()) }
    factory { GetUserProfileUseCase(get()) }

    // O HomeViewModel agora recebe dois Use Cases
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AlbumsViewModel(get()) }
}