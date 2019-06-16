package com.aura.project.rickandmortywiki

import androidx.recyclerview.widget.DiffUtil

class CharDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.name == newItem.name
                && oldItem.image == newItem.image
                && oldItem.status == newItem.status
}