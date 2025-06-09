package com.jeffersonfsferreira.spotifycloneapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeffersonfsferreira.spotifycloneapp.databinding.ItemPlaylistBinding
import com.jeffersonfsferreira.spotifycloneapp.domain.model.Playlist

class PlaylistAdapter(private val onItemClick: (Playlist) -> Unit) :
    ListAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = getItem(position)
        holder.bind(playlist)
    }

    inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(playlist: Playlist) {
            binding.tvPlaylistName.text = playlist.name
            binding.tvPlaylistOwner.text = "Por: ${playlist.ownerName}"
            binding.tvPlaylistPublicStatus.text = if (playlist.isPublic) "(Pública)" else "(Privada)"

            val imageUrl = playlist.images?.maxByOrNull { it.width ?: 0 }?.url
            Glide.with(binding.ivPlaylistImage.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_dialog_alert)
                .into(binding.ivPlaylistImage)
        }
    }

    class PlaylistDiffCallback: DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }

    }

}