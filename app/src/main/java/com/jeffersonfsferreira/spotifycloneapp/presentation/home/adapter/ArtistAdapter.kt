package com.jeffersonfsferreira.spotifycloneapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeffersonfsferreira.spotifycloneapp.databinding.ItemArtistBinding
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Artist

class ArtistAdapter(private val onItemClick: (Artist) -> Unit) :
    ListAdapter<Artist, ArtistAdapter.ArtistViewHolder>(ArtistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = getItem(position)
        holder.bind(artist)
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Configura o click listener para o item completo
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(artist: Artist) {
            binding.tvArtistName.text = artist.name
            binding.tvArtistGenres.text = "Gêneros: ${artist.genres?.joinToString(", ") ?: "N/A"}"

            // Carrega a imagem do artista usando Glide
            // Pega a primeira imagem de tamanho razoável (ou a maior se não houver opção)
            val imageUrl = artist.images?.maxByOrNull { it.width ?: 0 }?.url
            Glide.with(binding.ivArtistImage.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_dialog_alert)
                .into(binding.ivArtistImage)
        }
    }

    class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }
}