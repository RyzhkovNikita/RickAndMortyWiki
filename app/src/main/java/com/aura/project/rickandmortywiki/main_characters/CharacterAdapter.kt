package com.aura.project.rickandmortywiki.main_characters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character
import com.bumptech.glide.Glide

class CharacterAdapter(private val _context: Context) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var charList: List<Character> = ArrayList()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(CharDiffCallback(field, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int =
        charList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(_context, parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(charList[position])

    class CharacterViewHolder(private val context: Context, parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.character_card, parent, false)) {
        private var avatar: ImageView = itemView.findViewById(R.id.char_avatar)
        private var name: TextView = itemView.findViewById(R.id.char_name_tv)


        fun bind(character: Character) {
            name.text = character.name
            Glide.with(context).load(character.image).into(avatar)
        }
    }
}