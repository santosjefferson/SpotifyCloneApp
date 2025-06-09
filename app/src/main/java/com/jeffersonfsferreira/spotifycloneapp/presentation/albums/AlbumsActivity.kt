package com.jeffersonfsferreira.spotifycloneapp.presentation.albums

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jeffersonfsferreira.spotifycloneapp.databinding.ActivityAlbumsBinding
import com.jeffersonfsferreira.spotifycloneapp.presentation.albums.adapter.AlbumAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAlbumsBinding
    private val albumsViewModel: AlbumsViewModel by viewModel()
    private lateinit var albumAdapter: AlbumAdapter

    companion object {
        const val EXTRA_ARTIST_ID = "extra_artist_id"
        const val EXTRA_ARTIST_NAME = "extra_artist_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val artistId = intent.getStringExtra(EXTRA_ARTIST_ID)
        val artistName = intent.getStringExtra(EXTRA_ARTIST_NAME)

        if (artistId == null) {
            Toast.makeText(this, "ID do artista não fornecido.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.tvArtistNameAlbums.text = "Álbuns de $artistName"

        setupRecyclerView()
        observeViewModel()

        albumsViewModel.loadArtistAlbums(artistId, limit = 20, offset = 0)
    }

    private fun setupRecyclerView() {
        albumAdapter = AlbumAdapter { album ->
            Toast.makeText(this, "Clicou no álbum: ${album.name}", Toast.LENGTH_SHORT).show()
            // TODO: Implementar ação ao clicar no álbum (ex: tocar, ver detalhes)
        }
        binding.rvAlbums.adapter = albumAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    albumsViewModel.albums.collectLatest { albums ->
                        albumAdapter.submitList(albums)
                        binding.tvArtistNameAlbums.text = "${binding.tvArtistNameAlbums.text} (${albums.size})"
                    }
                }

                launch {
                    albumsViewModel.isLoading.collectLatest { isLoading ->
                        binding.progressBarAlbums.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    albumsViewModel.error.collectLatest { errorMessage ->
                        if (errorMessage != null) {
                            binding.tvErrorMessageAlbums.text = errorMessage
                            binding.tvErrorMessageAlbums.visibility = View.VISIBLE
                            Toast.makeText(this@AlbumsActivity, errorMessage, Toast.LENGTH_LONG).show()
                        } else {
                            binding.tvErrorMessageAlbums.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}