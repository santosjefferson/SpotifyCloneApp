package com.jeffersonfsferreira.spotifycloneapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.jeffersonfsferreira.spotifycloneapp.data.local.TokenManager
import com.jeffersonfsferreira.spotifycloneapp.databinding.ActivityMainBinding
import com.jeffersonfsferreira.spotifycloneapp.domain.model.UserProfile
import com.jeffersonfsferreira.spotifycloneapp.presentation.albums.AlbumsActivity
import com.jeffersonfsferreira.spotifycloneapp.presentation.auth.AuthActivity
import com.jeffersonfsferreira.spotifycloneapp.presentation.home.HomeViewModel
import com.jeffersonfsferreira.spotifycloneapp.presentation.home.adapter.ArtistAdapter
import com.jeffersonfsferreira.spotifycloneapp.presentation.main.PlaylistAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tokenManager: TokenManager by inject()
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var artistAdapter: ArtistAdapter
    private lateinit var playlistAdapter: PlaylistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupListeners()
        observeViewModelData()

        homeViewModel.loadUserProfile()
    }

    private fun setupRecyclerViews() {
        artistAdapter = ArtistAdapter { artist ->
            val intent = Intent(this, AlbumsActivity::class.java).apply {
                putExtra(AlbumsActivity.EXTRA_ARTIST_ID, artist.id)
                putExtra(AlbumsActivity.EXTRA_ARTIST_NAME, artist.name)
            }
            startActivity(intent)
        }
        binding.rvArtists.adapter = artistAdapter

        playlistAdapter = PlaylistAdapter { playlist ->
            Toast.makeText(this, "Clicou na playlist: ${playlist.name}", Toast.LENGTH_SHORT).show()
            // TODO: Implementar ação ao clicar na playlist
        }
        binding.rvPlaylists.adapter = playlistAdapter
    }

    private fun setupListeners() {
        // TODO: Mover o botão de logout para uma tela de perfil se houver tempo.
        //binding.btnLogout.setOnClickListener {
        //    performLogout()
        //}
    }

    private fun observeViewModelData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    homeViewModel.userProfile.collectLatest { userProfile -> // homeViewModel precisará expor 'userProfile'
                        updateUserProfileUI(userProfile)
                    }
                }

                launch {
                    homeViewModel.artists.collectLatest { artists ->
                        artistAdapter.submitList(artists)
                        binding.tvTopArtistsTitle.text = "Seus Top Artistas (${artists.size})"
                    }
                }

                launch {
                    homeViewModel.playlists.collectLatest { playlists -> // homeViewModel precisará expor 'playlists'
                        playlistAdapter.submitList(playlists)
                        binding.tvPlaylistsTitle.text = "Suas Playlists (${playlists.size})"
                    }
                }

                launch {
                    homeViewModel.isLoading.collectLatest { isLoading ->
                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    homeViewModel.error.collectLatest { errorMessage ->
                        if (errorMessage != null) {
                            binding.tvErrorMessage.text = errorMessage
                            binding.tvErrorMessage.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                        } else {
                            binding.tvErrorMessage.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun performLogout() {
        tokenManager.clearAccessToken()
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun updateUserProfileUI(userProfile: UserProfile?) {
        userProfile?.let {
            binding.tvGreeting.text = "Olá, ${it.displayName ?: "Usuário"}"
            it.images?.maxByOrNull { img -> img.width ?: 0 }?.url?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .circleCrop() // Para deixar a imagem redonda (opcional)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(binding.ivUserProfileImage)
            } ?: run {
                binding.ivUserProfileImage.setImageResource(android.R.drawable.ic_menu_gallery) // Imagem padrão
            }
        } ?: run {
            binding.tvGreeting.text = "Olá!" // Saudação padrão
            binding.ivUserProfileImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }
}