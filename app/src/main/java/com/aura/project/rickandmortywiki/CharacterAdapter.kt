package com.aura.project.rickandmortywiki

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(diffUtilCallback: DiffUtil.ItemCallback<Character>, private val _context: Context) :
    PagedListAdapter<Character, CharacterAdapter.CharacterViewHolder>(diffUtilCallback) {

    private var _list:List<Character> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(_context, parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
            holder.bind(_list[position])

    class CharacterViewHolder(val context: Context, parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.character_card, parent, false)) {
        private var avatar: ImageView = itemView.findViewById(R.id.char_avatar)
        private var name: TextView = itemView.findViewById(R.id.char_name_tv)


        fun bind(character: Character) {
            name.text = character.name
            Glide.with(context).load(character.image).into(avatar)
        }
    }
}